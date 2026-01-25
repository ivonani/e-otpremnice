package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.OdredisteEntityId;
import org.eotpremnice.mapper.OdredisteMapper;
import org.eotpremnice.model.Odrediste;
import org.eotpremnice.repository.OdredisteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OdredisteService {

    private final OdredisteRepository repository;
    private final OdredisteMapper mapper;

    public Odrediste loadOdrediste(String idFirme, String tipDokumenta, Integer iddok) {
        return repository.findById(
                        OdredisteEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null);
    }
}