package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.KupacEntity;
import org.eotpremnice.entity.id.KupacEntityId;
import org.eotpremnice.mapper.KupacMapper;
import org.eotpremnice.model.Kupac;
import org.eotpremnice.repository.KupacRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class KupacService {

    private final KupacRepository repository;
    private final KupacMapper mapper;

    public Kupac loadKupac(String idFirme, String tipDokumenta, Long idDok) {
        KupacEntityId id = KupacEntityId.builder()
                .iDFirme(idFirme)
                .tipDokumenta(tipDokumenta)
                .iddok(idDok)
                .build();
        KupacEntity kupac = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Kupac not found: IDFirme=" + idFirme + ", TipDokumenta=" + tipDokumenta + ", IDDok=" + idDok
                ));

        return mapper.toModel(kupac);
    }

}
