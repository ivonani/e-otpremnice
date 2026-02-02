package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.LogEntity;
import org.eotpremnice.entity.id.LogEntityId;
import org.eotpremnice.model.EoLogEntry;
import org.eotpremnice.repository.EoLogEntryRepository;
import org.eotpremnice.repository.EoLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.eotpremnice.model.FirmaKey;

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
    public void updateLog(
            FirmaKey key,
            Integer iddok,
            Integer zaSlanje,
            Integer otpremljen,
            Integer idError,
            String sefId,
            String status,
            String fullJsonResponse,
            LocalDateTime datumOtpreme,
            Integer promenjenStatus
    ) {
        LogEntity e = repository.findById(
                        LogEntityId.builder()
                                .iDFirme(key.getIdFirme())
                                .tipDokumenta(key.getTipDokumenta())
                                .iddok(iddok)
                                .build()
                )
                .orElseThrow(() -> new IllegalStateException(
                        "eoLog not found for key: " + key.getIdFirme() + "/" + key.getTipDokumenta() + "/" + iddok
                ));

        e.setZaSlanje(zaSlanje);

        e.setDatumSlanja(LocalDateTime.now());
        if (datumOtpreme != null) {
            e.setDatumOtpreme(datumOtpreme);
        }
        if (sefId != null) {
            e.setSefId(sefId);
        }
        if (promenjenStatus != null) {
            e.setPromenjenStatus(promenjenStatus);
        } else {
            e.setOtpremljen(otpremljen);
        }
        e.setIdError(idError);
        e.setResponseStatus(fullJsonResponse);
        if (status != null ) {
            e.setStatus(status);
        }
        e.setIdRacunar(null);

        repository.save(e);
    }

    @Transactional
    public void updateLogStatus(
            FirmaKey key,
            Integer iddok,
            Integer zaSlanje,
            Integer idError,
            String status,
            String fullJsonResponse,
            Integer obradjenStatus
    ) {
        LogEntity e = repository.findById(
                        LogEntityId.builder()
                                .iDFirme(key.getIdFirme())
                                .tipDokumenta(key.getTipDokumenta())
                                .iddok(iddok)
                                .build()
                )
                .orElseThrow(() -> new IllegalStateException(
                        "eoLog not found for key: " + key.getIdFirme() + "/" + key.getTipDokumenta() + "/" + iddok
                ));

        e.setZaSlanje(zaSlanje);
        e.setDatumSlanja(LocalDateTime.now());
        e.setIdError(idError);
        e.setResponseStatus(fullJsonResponse);
        e.setObradjenStatus(obradjenStatus);
        if (status != null) {
            e.setStatus(status);
        }
        e.setIdRacunar(null);

        repository.save(e);
    }
}