package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.mapper.StavkeMapper;
import org.eotpremnice.model.Stavke;
import org.eotpremnice.repository.StavkeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StavkeService {

    private final StavkeRepository stavkeRepository;
    private final StavkeMapper stavkeMapper;

    public List<Stavke> loadStavke(String idFirme, String tipDokumenta, Integer iddok) {
        return stavkeRepository
                .findByIdIdFirmeAndIdTipDokumentaAndIdIddok(idFirme, tipDokumenta, iddok)
                .stream()
                .map(stavkeMapper::toModel)
                .collect(Collectors.toList());
    }
}