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
import org.eotpremnice.xml.writer.XmlFileWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDate;
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

    @Override
    public void run(String... args) {

        System.out.println("Start...");

        String idRacunar = requireIdRacunar(args);

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
                    // 4) GET changes (date = today, requestId = entry.requestId)
                    LocalDate today = LocalDate.now();
                    ResponseEntity<String> getResp = sefClient.getSupplierChangesRaw(api.getUrl(), api.getFile(), today, entry.getRequestId());

                    if (HttpStatus.OK.equals(getResp.getStatusCode())) {
                        String json = getResp.getBody();
                        SupplierChangesResponse parsed = sefClient.parseChanges(objectMapper, json);

                        // uzmi prvi item (po slici totalCount=1)
                        SupplierChangesResponse.Item item0 = (parsed.getItems() != null && !parsed.getItems().isEmpty()) ? parsed.getItems().get(0) : null;
                        SupplierChangesResponse.DespatchAdvice da = (item0 != null && item0.getData() != null) ? item0.getData().getDespatchAdvice() : null;

                        String sefId = (da != null) ? da.getId() : null;
                        String status = (da != null) ? da.getStatus() : null;

                        // 5) update eoLog + semafor tek posle GET
                        eoLogService.updateAfterSuccess(
                                key.getIdFirme(),
                                key.getTipDokumenta(),
                                entry.getIdDok(),
                                sefId,     // SEF_ID
                                status, // STATUS
                                json
                        );
                        semaforService.resetEDokument(idRacunar);
                    } else {

                    }
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private String requireIdRacunar(String[] args) {
        if (args == null || args.length != 1 || args[0].trim().isEmpty()) {
            return "00015";
//            throw new IllegalArgumentException("Usage: app <IDRacunar> (npr. 00001)");
        }
        return args[0].trim();
    }
}
