package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.mapper.FirmaToPosiljalacMapper;
import org.eotpremnice.model.Posiljalac;
import org.eotpremnice.repository.FirmaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FirmaService {

    private final FirmaRepository repository;
    private final FirmaToPosiljalacMapper mapper;

    public Posiljalac loadPosiljalac(Long idFirma) {
        FirmaEntity firma = repository.findAll().get(0);
//                .orElseThrow(() -> new IllegalArgumentException("Firma not found: IDFIRMA=" + idFirma));
        return mapper.toModel(firma);
    }

}
