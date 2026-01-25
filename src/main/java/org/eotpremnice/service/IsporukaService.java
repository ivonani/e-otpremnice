package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.IsporukaEntityId;
import org.eotpremnice.mapper.IsporukaMapper;
import org.eotpremnice.model.Isporuka;
import org.eotpremnice.repository.IsporukaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IsporukaService {

    private final IsporukaRepository repository;
    private final IsporukaMapper mapper;

    public Isporuka loadIsporuka(String idFirme, String tipDokumenta, Integer iddok) {
        return repository.findById(
                        IsporukaEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null); // ili baci exception, kako ti više odgovara
    }
}