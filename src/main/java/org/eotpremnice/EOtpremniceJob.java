package org.eotpremnice;

import lombok.RequiredArgsConstructor;
import oasis.names.specification.ubl.schema.xsd.despatchadvice_2.DespatchAdviceType;
import org.eotpremnice.model.EoLogEntry;
import org.eotpremnice.model.FirmaKey;
import org.eotpremnice.model.PristupniParametri;
import org.eotpremnice.service.EoLogService;
import org.eotpremnice.service.PristupniParametriService;
import org.eotpremnice.service.SystblParamService;
import org.eotpremnice.xml.builder.DespatchAdviceXmlBuilder;
import org.eotpremnice.xml.writer.XmlFileWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EOtpremniceJob implements CommandLineRunner {

    private final JdbcTemplate jdbc;
    private final SystblParamService systblParamService;
    private final PristupniParametriService pristupniParametriService;
    private final EoLogService eoLogService;

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
            DespatchAdviceType advice = DespatchAdviceXmlBuilder.builder(entry.getIdDok());
            try {
                XmlFileWriter.write(advice);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private String requireIdRacunar(String[] args) {
        if (args == null || args.length != 1 || args[0].trim().isEmpty()) {
            return "00001";
//            throw new IllegalArgumentException("Usage: app <IDRacunar> (npr. 00001)");
        }
        return args[0].trim();
    }
}
