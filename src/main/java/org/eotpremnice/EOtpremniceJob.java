package org.eotpremnice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import org.eotpremnice.client.SefClient;
import org.eotpremnice.model.*;
import org.eotpremnice.service.EoLogService;
import org.eotpremnice.service.PristupniParametriService;
import org.eotpremnice.service.SemaforService;
import org.eotpremnice.service.SystblParamService;
import org.eotpremnice.xml.builder.DespatchAdviceXmlBuilder;
import org.eotpremnice.xml.writer.ErrorFileWriter;
import org.eotpremnice.xml.writer.XmlFileWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EOtpremniceJob implements CommandLineRunner {

    private final SystblParamService systblParamService;
    private final PristupniParametriService pristupniParametriService;
    private final EoLogService eoLogService;
    private final DespatchAdviceXmlBuilder builder;
    private final SefClient sefClient;
    private final ObjectMapper objectMapper;
    private final SemaforService semaforService;

    private final static String DOCUMENT_REQUEST_SUCCEEDED = "DocumentRequest.Succeeded";
    private final static String DOCUMENT_REQUEST_PENDING = "DocumentRequest.Pending";

    @Override
    public void run(String... args) {

        System.out.println("Start...");

        String idRacunar = requireIdRacunar(args);
        if (idRacunar == null) return;

        try {
            PristupniParametri api = pristupniParametriService.loadApiAccess();
            FirmaKey key = systblParamService.loadFirmaKey(idRacunar);

            List<EoLogEntry> logEntries = eoLogService.loadDocumentsToSend(
                    key.getIdFirme(), key.getTipDokumenta(), idRacunar
            );
            for (EoLogEntry entry : logEntries) {
                if (entry.getKomanda().equals("SEND_EO")) {
                    try {
                        DespatchAdviceType advice = builder.builder(key.getIdFirme(), key.getTipDokumenta(), entry.getIdDok());

                        String pfdLocation = systblParamService.loadXmlLocation(idRacunar);
                        Path xmlPath = XmlFileWriter.write(advice, pfdLocation, entry.getRequestId());

                        ResponseEntity<String> responseEntity = sefClient.sendUblXml(xmlPath, api.getUrl(), api.getFile(), entry.getRequestId());
                        sleepQuietly(2000);
                        if (responseEntity.getStatusCode().is2xxSuccessful()) {

                            ResponseEntity<String> getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), LocalDate.now(), entry.getRequestId());
                            sleepQuietly(2000);
                            String json = getResp.getBody();

                            if (getResp.getStatusCode().is2xxSuccessful()) {

                                SupplierChangesResponse parsed = sefClient.parseChanges(objectMapper, json);

                                SupplierChangesResponse.Item item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                                SupplierChangesResponse.DataBlock data = (item0 != null && item0.getData() != null) ? item0.getData() : null;
                                int i = 15;

                                while (item0 != null && DOCUMENT_REQUEST_PENDING.equals(item0.getType()) && i >= 0) {

                                    getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), LocalDate.now(), entry.getRequestId());
                                    sleepQuietly(5000);
                                    json = getResp.getBody();

                                    parsed = sefClient.parseChanges(objectMapper, json);

                                    item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                                    data = (item0 != null && item0.getData() != null) ? item0.getData() : null;

                                    i--;
                                }

                                if (item0 != null && DOCUMENT_REQUEST_SUCCEEDED.equals(item0.getType())) {

                                    String sefId = (data != null) ? data.getDocumentId() : null;

                                    updateLogSuccess(entry, key, sefId, json);
                                } else {
                                    updateLogError(key, entry, 100, json);
                                }
                            } else {
                                updateLogError(key, entry, 100, json);
                            }
                        } else {
                            updateLogError(key, entry, responseEntity.getStatusCodeValue(), responseEntity.getBody());
                        }
                    } catch (ResourceAccessException timeoutEx) {
                        // ⏱ TIMEOUT (nema odgovora za 1 min)
                        updateLogError(key, entry, 100, "Isteklo je vreme cekanja, server nema odziv nakon 1 minuta");

                    } catch (Exception e) {
                        updateLogError(key, entry, 100, e.getLocalizedMessage());
                    }
                } else if (entry.getKomanda().equals("STATUS")) {
                    ResponseEntity<String> statusResp =
                            sefClient.getDespatchAdviceStatus(
                                    api.getUrl(),
                                    api.getFile(),
                                    entry.getSefId()
                            );
                    sleepQuietly(2000);
                    String jsonStatus = statusResp.getBody();

                    if (statusResp.getStatusCode().is2xxSuccessful()) {

                        try {
                            DespatchAdviceStatusResponse parsedData = sefClient.parseChangesStatus(objectMapper, jsonStatus);

                            String status = (parsedData != null) ? parsedData.getStatus() : null;
                            updateLogSuccessStatus(entry, key, jsonStatus, status);
                        } catch (Exception e) {
                            updateLogErrorStatus(entry, key, jsonStatus, 100);
                        }
                    } else {
                        updateLogErrorStatus(entry, key, jsonStatus, statusResp.getStatusCodeValue());
                    }
                }
            }
        } catch (Exception exception) {
            ErrorFileWriter.write(exception.getLocalizedMessage());
        } finally {
            semaforService.resetEDokument(idRacunar);
        }
    }

    private void updateLogSuccess(EoLogEntry entry, FirmaKey key, String sefId, String json) {
        eoLogService.updateLog(
                key,
                entry.getIdDok(),
                0,
                1,
                200,
                sefId,
                "Sent",
                json,
                LocalDateTime.now()
        );
    }

    private void updateLogSuccessStatus(EoLogEntry entry, FirmaKey key, String json, String status) {
        eoLogService.updateLogStatus(
                key,
                entry.getIdDok(),
                0,
                200,
                status,
                json,
                1
        );
    }

    private void updateLogError(FirmaKey key, EoLogEntry entry, int idError, String e) {
        eoLogService.updateLog(
                key,
                entry.getIdDok(),
                0,
                0,
                idError,
                null,
                null,
                e,
                null
        );
    }

    private void updateLogErrorStatus(EoLogEntry entry, FirmaKey key, String json, Integer idError) {
        eoLogService.updateLogStatus(
                key,
                entry.getIdDok(),
                0,
                idError,
                null,
                json,
                0
        );
    }

    private String requireIdRacunar(String[] args) {
        if (args != null && args.length == 1) {
            return args[0].trim();
        } else {
            ErrorFileWriter.write("Nepravilan IDRacunar");
//            return "00015";
            return null;
        }
    }

    private void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }

}
