package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.FirmaKey;
import org.eotpremnice.repository.SystblParamJdbcRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystblParamService {

    private final SystblParamJdbcRepository repo;

    public FirmaKey loadFirmaKey(String idRacunar) {

        String idFirme = repo.readNcharParam(451);
        String tipDokumenta = repo.readNcharParam(452);

        if (idFirme == null || idFirme.trim().isEmpty()) {
            throw new IllegalStateException("IDFirme is empty for IDRacunar=" + idRacunar);
        }
        if (tipDokumenta == null || tipDokumenta.trim().isEmpty()) {
            throw new IllegalStateException("TipDokumenta is empty for IDRacunar=" + idRacunar);
        }

        return new FirmaKey(idFirme.trim(), tipDokumenta.trim());
    }

    public String loadXmlLocation(String idRacunar) {

        String xmlLocation = repo.readNcharParam(455);

        if (xmlLocation == null || xmlLocation.trim().isEmpty()) {
            throw new IllegalStateException("XML location is empty for IDRacunar=" + idRacunar);
        }

        return xmlLocation;
    }
}
