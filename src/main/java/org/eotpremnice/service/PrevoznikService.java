package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.PrevoznikEntityId;
import org.eotpremnice.mapper.PrevoznikMapper;
import org.eotpremnice.model.Prevoznik;
import org.eotpremnice.repository.PrevoznikRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrevoznikService {

    private final PrevoznikRepository repository;
    private final PrevoznikMapper mapper;

    public Prevoznik loadPrevoznik(String idFirme, String tipDokumenta, Long iddok) {
        return repository.findById(
                        PrevoznikEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null);
    }
}