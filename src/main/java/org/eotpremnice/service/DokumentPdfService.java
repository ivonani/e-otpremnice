package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.mapper.DokumentPdfMapper;
import org.eotpremnice.model.DokumentPdf;
import org.eotpremnice.repository.DokumentPdfRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DokumentPdfService {

    private final DokumentPdfRepository repository;
    private final DokumentPdfMapper mapper;

    public List<DokumentPdf> loadPdfPrilozi(String idFirme, String tipDokumenta, Long iddok) {
        return repository.findByIdFirmeAndTipDokumentaAndIddok(idFirme, tipDokumenta, iddok)
                .stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());
    }
}
