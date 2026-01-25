package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.id.FirmEntityId;
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

    public Posiljalac loadPosiljalac(String idFirme, String tipDokumenta) {
        FirmEntityId id = FirmEntityId.builder()
                .iDFirme(idFirme)
                .tipDokumenta(tipDokumenta)
                .build();
        FirmaEntity firma = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nepravilni podaci u tabeli Firma: IDFirme=" + idFirme + ", TipDokumenta=" + tipDokumenta
                ));

        return mapper.toModel(firma);
    }

}
