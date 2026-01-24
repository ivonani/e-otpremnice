package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.EoLogEntry;
import org.eotpremnice.repository.EoLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EoLogService {

    private final EoLogRepository repository;

    public List<EoLogEntry> loadDocumentsToSend(String idFirme, String tipDokumenta, String idRacunar) {
        return repository.findIDDOKs(idFirme, tipDokumenta, idRacunar);
    }
}