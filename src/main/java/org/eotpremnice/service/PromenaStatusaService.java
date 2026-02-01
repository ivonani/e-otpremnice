package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.mapper.PromenaStatusaMapper;
import org.eotpremnice.model.PromenaStatusa;
import org.eotpremnice.repository.PromenaStatusaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromenaStatusaService {

    private final PromenaStatusaRepository repository;
    private final PromenaStatusaMapper mapper;

    public PromenaStatusa loadPromenaStatusa(String idFirme, String tipDokumenta, Integer iddok) {
        return repository.findByIdIdFirmeAndIdTipDokumentaAndIdIddokAndPoslato(
                idFirme, tipDokumenta, iddok, 0)
                .map(mapper::toModel)
                .orElse(null);
    }
}