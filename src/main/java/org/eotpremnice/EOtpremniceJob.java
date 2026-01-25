package org.eotpremnice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import org.eotpremnice.client.SefClient;
import org.eotpremnice.model.EoLogEntry;
import org.eotpremnice.model.FirmaKey;
import org.eotpremnice.model.PristupniParametri;
import org.eotpremnice.model.SupplierChangesResponse;
import org.eotpremnice.service.EoLogService;
import org.eotpremnice.service.PristupniParametriService;
import org.eotpremnice.service.SemaforService;
import org.eotpremnice.service.SystblParamService;
import org.eotpremnice.xml.builder.DespatchAdviceXmlBuilder;
import org.eotpremnice.xml.writer.ErrorFileWriter;
import org.eotpremnice.xml.writer.XmlFileWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
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

    @Override
    public void run(String... args) {

        System.out.println("Start...");

        String idRacunar = requireIdRacunar(args);

        try {
            PristupniParametri api = pristupniParametriService.loadApiAccess();
            FirmaKey key = systblParamService.loadFirmaKey(idRacunar);

            List<EoLogEntry> logEntries = eoLogService.loadDocumentsToSend(
                    key.getIdFirme(), key.getTipDokumenta(), idRacunar
            );
            for (EoLogEntry entry : logEntries) {
                DespatchAdviceType advice;
                try {
                    advice = builder.builder(key.getIdFirme(), key.getTipDokumenta(), entry.getIdDok());

                    String pfdLocation = systblParamService.loadXmlLocation(idRacunar);
                    Path xmlPath = XmlFileWriter.write(advice, pfdLocation, entry.getRequestId());

                    ResponseEntity<String> responseEntity = sefClient.sendUblXml(xmlPath, api.getUrl(), api.getFile(), entry.getRequestId());
                    if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
                        LocalDate today = LocalDate.now();
                        ResponseEntity<String> getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), today, entry.getRequestId());
                        String json = getResp.getBody();

                        if (HttpStatus.OK.equals(getResp.getStatusCode())) {

                            SupplierChangesResponse parsed = sefClient.parseChanges(objectMapper, json);

                            SupplierChangesResponse.Item item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                            SupplierChangesResponse.DespatchAdvice da = (item0 != null && item0.getData() != null) ? item0.getData().getDespatchAdvice() : null;

                            if (item0 != null && DOCUMENT_REQUEST_SUCCEEDED.equals(item0.getType())) {

                                String sefId = (da != null) ? da.getDocumentId() : null;
                                String status = (da != null) ? da.getStatus() : null;

                                eoLogService.updateLog(
                                        key,
                                        entry.getIdDok(),
                                        0,
                                        1,
                                        200,
                                        sefId,
                                        status,
                                        json,
                                        LocalDateTime.now()
                                );
                            } else {
                                eoLogService.updateLog(
                                        key,
                                        entry.getIdDok(),
                                        0,
                                        0,
                                        getResp.getStatusCodeValue(),
                                        null,
                                        null,
                                        json,
                                        null
                                );
                            }
                        } else {
                            eoLogService.updateLog(
                                    key,
                                    entry.getIdDok(),
                                    0,
                                    0,
                                    100,
                                    null,
                                    null,
                                    json,
                                    null
                            );
                        }
                    } else {
                        eoLogService.updateLog(
                                key,
                                entry.getIdDok(),
                                0,
                                0,
                                responseEntity.getStatusCodeValue(),
                                null,
                                null,
                                responseEntity.getBody(),
                                null
                        );
                    }
                } catch (ResourceAccessException timeoutEx) {
                    // ⏱ TIMEOUT (nema odgovora za 1 min)
                    eoLogService.updateLog(
                            key,
                            entry.getIdDok(),
                            0,
                            0,
                            100,
                            null,
                            null,
                            "Isteklo je vreme cekanja, server nema odziv nakon 1 minuta",
                            null
                    );

                } catch (Exception e) {
                    eoLogService.updateLog(
                            key,
                            entry.getIdDok(),
                            0,
                            0,
                            100,
                            null,
                            null,
                            e.getLocalizedMessage(),
                            null
                    );
                }
            }
        } catch (Exception exception) {
            ErrorFileWriter.write(exception.getLocalizedMessage());
        } finally {
            semaforService.resetEDokument(idRacunar);
        }
    }

    private String requireIdRacunar(String[] args) {
        if (args == null || args.length != 1 || args[0].trim().isEmpty()) {
            ErrorFileWriter.write("Nepravilan IDRacunar, kao default koristi se 00015");
            return "00015";
        }
        return args[0].trim();
    }
}
