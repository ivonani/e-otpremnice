package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.KurirEntityId;
import org.eotpremnice.mapper.KurirMapper;
import org.eotpremnice.model.Kurir;
import org.eotpremnice.repository.KurirRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KurirService {

    private final KurirRepository repository;
    private final KurirMapper mapper;

    public Kurir loadKurir(String idFirme, String tipDokumenta, Integer iddok) {
        return repository.findById(
                        KurirEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null);
    }
}
