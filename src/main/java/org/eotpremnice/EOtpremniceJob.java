package org.eotpremnice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.applicationresponse_2.ApplicationResponseType;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import org.eotpremnice.client.SefClient;
import org.eotpremnice.model.*;
import org.eotpremnice.service.*;
import org.eotpremnice.xml.builder.ApplicationResponseXmlBuilder;
import org.eotpremnice.xml.builder.DespatchAdviceXmlBuilder;
import org.eotpremnice.xml.writer.ErrorFileWriter;
import org.eotpremnice.xml.writer.XmlFileWriter;
import org.eotpremnice.xml.writer.XmlFileWriterStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    private final ApplicationResponseXmlBuilder statusBuilder;
    private final PromenaStatusaService promenaStatusaService;

    private final static String DOCUMENT_REQUEST_SUCCEEDED = "DocumentRequest.Succeeded";
    private final static String DOCUMENT_REQUEST_PENDING = "DocumentRequest.Pending";

    @Override
    public void run(String... args) {

        System.out.println("Start...");

        String idRacunar = requireIdRacunar(args);
        if (idRacunar == null) return;

        PristupniParametri api;
        FirmaKey key;
        List<EoLogEntry> logEntries;

        try {
            api = pristupniParametriService.loadApiAccess();
            key = systblParamService.loadFirmaKey(idRacunar);

            logEntries = eoLogService.loadDocumentsToSend(
                    key.getIdFirme(), key.getTipDokumenta(), idRacunar
            );

        } catch (Exception e) {
            ErrorFileWriter.write(
                    "Error before processing entries. idRacunar=" + idRacunar, e
            );
            return;
        }

        try {
            for (EoLogEntry entry : logEntries) {
                switch (entry.getKomanda()) {
                    case "SEND_EO":
                        processSendEO(entry, key, idRacunar, api);
                        break;
                    case "STATUS":
                        processStatus(entry, api, key);
                        break;
                    case "CHANGE_ST":
                        processChangeST(entry, key, idRacunar, api);
                        break;
                    case "PDF":
                        processLoadPDF(entry, key, api);
                        break;
                }
            }
        } catch (Exception e) {
            ErrorFileWriter.write("Error during processing entries. idRacunar=" + idRacunar, e);
        } finally {
            semaforService.resetEDokument(idRacunar);
        }
    }

    private void processLoadPDF(EoLogEntry entry, FirmaKey key, PristupniParametri api) {
        ResponseEntity<byte[]> response =
                sefClient.downloadPdf(
                        api.getUrl(),
                        api.getFile(),
                        entry.getSefId()
                );
        sleepQuietly(2000);

        byte[] responseBody = response.getBody();
        if (responseBody != null) {
            if (response.getStatusCode().is2xxSuccessful()) {
                Path targetPath = Paths.get(key.getPutanjaZaPDF());
                Path pdfPath = targetPath.resolve(entry.getSefId().trim() + ".pdf");
                try {
                    Files.write(pdfPath, response.getBody());
                    updateLogSuccessStatus(entry, key, "OK", null, null);
                } catch (IOException e) {
                    updateLogErrorStatus(entry, key, "Greska pri cuvanje pdf-a", response.getStatusCodeValue(), null);
                }
            } else {
                updateLogErrorStatus(entry, key, Arrays.toString(responseBody), response.getStatusCodeValue(), null);
            }
        } else {
            updateLogErrorStatus(entry, key, "Response body je null", response.getStatusCodeValue(), null);
        }
    }

    private void processChangeST(EoLogEntry entry, FirmaKey key, String idRacunar, PristupniParametri api) {
        PromenaStatusa promenaStatusa = promenaStatusaService.loadPromenaStatusa(key.getIdFirme(), key.getTipDokumenta(), entry.getIdDok());
        if (promenaStatusa != null) {
            try {

            ApplicationResponseType response = statusBuilder.builder(promenaStatusa);

            String pfdLocation = systblParamService.loadXmlLocation(idRacunar);
            Path xmlPath = XmlFileWriterStatus.write(response, pfdLocation, promenaStatusa.getRequestId());

            processGETRequest(entry, api, key, xmlPath, promenaStatusa.getRequestId(), true);
            } catch (ResourceAccessException timeoutEx) {
                updateLogError(key, entry, 100, "Isteklo je vreme cekanja, server nema odziv nakon 1 minuta", true);
            } catch (Exception e) {
                updateLogError(key, entry, 100, e.getLocalizedMessage(), true);
            }
        } else {
            updateLogError(key, entry, 100, "Nepravilni podaci u tabeli PromenaStatusa", true);
        }
    }

    private void processStatus(EoLogEntry entry, PristupniParametri api, FirmaKey key) {
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
                updateLogSuccessStatus(entry, key, jsonStatus, status, 1);
            } catch (Exception e) {
                updateLogErrorStatus(entry, key, jsonStatus, 100, 0);
            }
        } else {
            updateLogErrorStatus(entry, key, jsonStatus, statusResp.getStatusCodeValue(), 0);
        }
    }

    private void processSendEO(EoLogEntry entry, FirmaKey key, String idRacunar, PristupniParametri api) {
        try {
            DespatchAdviceType advice = builder.builder(key.getIdFirme(), key.getTipDokumenta(), entry.getIdDok());

            String pfdLocation = systblParamService.loadXmlLocation(idRacunar);
            Path xmlPath = XmlFileWriter.write(advice, pfdLocation, entry.getRequestId());

            processGETRequest(entry, api, key, xmlPath, entry.getRequestId(), false);
        } catch (ResourceAccessException timeoutEx) {
            updateLogError(key, entry, 100, "Isteklo je vreme cekanja, server nema odziv nakon 1 minuta", false);
        } catch (Exception e) {
            updateLogError(key, entry, 100, e.getLocalizedMessage(), false);
        }
    }

    private void processGETRequest(EoLogEntry entry, PristupniParametri api, FirmaKey key, Path xmlPath, String requestId, boolean stateChange) throws Exception {
        ResponseEntity<String> responseEntity = sefClient.sendUblXml(xmlPath, api.getUrl(), api.getFile(), requestId);
        sleepQuietly(2000);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            ResponseEntity<String> getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), LocalDate.now(), requestId);
            sleepQuietly(2000);
            String json = getResp.getBody();

            if (getResp.getStatusCode().is2xxSuccessful()) {

                SupplierChangesResponse parsed = sefClient.parseChanges(objectMapper, json);

                SupplierChangesResponse.Item item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                SupplierChangesResponse.DataBlock data = (item0 != null && item0.getData() != null) ? item0.getData() : null;
                int i = 15;

                while (item0 != null && DOCUMENT_REQUEST_PENDING.equals(item0.getType()) && i >= 0) {

                    getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), LocalDate.now(), requestId);
                    sleepQuietly(5000);
                    json = getResp.getBody();

                    parsed = sefClient.parseChanges(objectMapper, json);

                    item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                    data = (item0 != null && item0.getData() != null) ? item0.getData() : null;

                    i--;
                }

                if (item0 != null && DOCUMENT_REQUEST_SUCCEEDED.equals(item0.getType())) {

                    String sefId = (data != null) ? data.getDocumentId() : null;

                    updateLogSuccess(entry, key, sefId, json, stateChange);
                } else {
                    updateLogError(key, entry, 100, json, stateChange);
                }
            } else {
                updateLogError(key, entry, 100, json, stateChange);
            }
        } else {
            updateLogError(key, entry, responseEntity.getStatusCodeValue(), responseEntity.getBody(), stateChange);
        }
    }

    private void updateLogSuccess(EoLogEntry entry, FirmaKey key, String sefId, String json, boolean promenaStatusa) {
        eoLogService.updateLog(
                key,
                entry.getIdDok(),
                0,
                1,
                200,
                promenaStatusa ? null : sefId,
                promenaStatusa ? "Cancelled" : "Sent",
                json,
                LocalDateTime.now(),
                promenaStatusa ? 1 : null
        );
    }

    private void updateLogSuccessStatus(EoLogEntry entry, FirmaKey key, String json, String status, Integer obradjenStatus) {
        eoLogService.updateLogStatus(
                key,
                entry.getIdDok(),
                0,
                200,
                status,
                json,
                obradjenStatus
        );
    }

    private void updateLogError(FirmaKey key, EoLogEntry entry, int idError, String e, boolean promenaStatusa) {
        eoLogService.updateLog(
                key,
                entry.getIdDok(),
                0,
                0,
                idError,
                null,
                null,
                e,
                null,
                promenaStatusa ? 0 : null
        );
    }

    private void updateLogErrorStatus(EoLogEntry entry, FirmaKey key, String json, Integer idError, Integer obradjenStatus) {
        eoLogService.updateLogStatus(
                key,
                entry.getIdDok(),
                0,
                idError,
                null,
                json,
                obradjenStatus
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
