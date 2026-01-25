package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.MagacinEntityId;
import org.eotpremnice.mapper.MagacinMapper;
import org.eotpremnice.model.Magacin;
import org.eotpremnice.repository.MagacinRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MagacinService {

    private final MagacinRepository repository;
    private final MagacinMapper mapper;

    public Magacin loadMagacin(String idFirme, String tipDokumenta, Integer iddok) {
        return repository.findById(
                        MagacinEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null);
    }
}