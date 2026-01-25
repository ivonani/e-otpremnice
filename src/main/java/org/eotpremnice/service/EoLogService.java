package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.LogEntity;
import org.eotpremnice.entity.id.LogEntityId;
import org.eotpremnice.model.EoLogEntry;
import org.eotpremnice.repository.EoLogEntryRepository;
import org.eotpremnice.repository.EoLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EoLogService {

    private final EoLogEntryRepository entryRepository;
    private final EoLogRepository repository;

    public List<EoLogEntry> loadDocumentsToSend(String idFirme, String tipDokumenta, String idRacunar) {
        return entryRepository.findIDDOKs(idFirme, tipDokumenta, idRacunar);
    }

    @Transactional
    public void updateAfterSuccess(
            String idFirme,
            String tipDokumenta,
            Integer iddok,
            String sefId,
            String status,
            String fullJsonResponse
    ) {
        LogEntity e = repository.findById(
                        LogEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .orElseThrow(() -> new IllegalStateException(
                        "eoLog not found for key: " + idFirme + "/" + tipDokumenta + "/" + iddok
                ));

        // prema slajdu:
        e.setZaSlanje(0);
        e.setOtpremljen(1);

        // ako su ti u bazi DATE/DATETIME:
        e.setDatumSlanja(LocalDateTime.now());
        e.setDatumOtpreme(LocalDateTime.now());

        e.setIdError(200);

        e.setSefId(sefId);
        e.setResponseStatus(fullJsonResponse);
        e.setStatus(status);

        e.setIdRacunar(null);

        repository.save(e);
    }

    @Transactional
    public void updateAfterFailure(
            String idFirme,
            String tipDokumenta,
            Integer iddok,
            Integer errorCode,
            String errorText
    ) {
        LogEntity e = repository.findById(
                        LogEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .orElseThrow(() -> new IllegalStateException(
                        "eoLog not found for key: " + idFirme + "/" + tipDokumenta + "/" + iddok
                ));

        e.setIdError(errorCode);
        e.setResponseStatus(errorText);

        repository.save(e);
    }
}