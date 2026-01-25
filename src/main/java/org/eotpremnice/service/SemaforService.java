package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.SemaforEntity;
import org.eotpremnice.repository.SemaforRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SemaforService {

    private final SemaforRepository semaforRepository;

    @Transactional
    public void resetEDokument(String idRacunar) {
        SemaforEntity s = semaforRepository.findByIdRacunar(idRacunar)
                .orElseThrow(() -> new IllegalStateException("Semafor not found for IDRacunar=" + idRacunar));

        s.setEDokument(0);
        semaforRepository.save(s);
    }
}