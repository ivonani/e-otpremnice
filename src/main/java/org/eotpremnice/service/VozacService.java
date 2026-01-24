package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.id.VozacEntityId;
import org.eotpremnice.mapper.VozacMapper;
import org.eotpremnice.model.Vozac;
import org.eotpremnice.repository.VozacRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class VozacService {

    private final VozacRepository repository;
    private final VozacMapper mapper;

    public Vozac loadVozac(String idFirme, String tipDokumenta, Long iddok) {
        return repository.findById(
                        VozacEntityId.builder()
                                .iDFirme(idFirme)
                                .tipDokumenta(tipDokumenta)
                                .iddok(iddok)
                                .build()
                )
                .map(mapper::toModel)
                .orElse(null);
    }
}