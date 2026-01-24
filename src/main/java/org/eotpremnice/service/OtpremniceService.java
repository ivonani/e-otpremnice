package org.eotpremnice.service;

import lombok.RequiredArgsConstructor;
import org.eotpremnice.entity.OtpremniceEntity;
import org.eotpremnice.entity.id.OtpremniceEntityId;
import org.eotpremnice.mapper.OtpremniceMapper;
import org.eotpremnice.model.Otpremnice;
import org.eotpremnice.repository.OtpremniceRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OtpremniceService {

    private final OtpremniceRepository repository;
    private final OtpremniceMapper mapper;

    public Otpremnice loadOtpremnice(String idFirme, String tipDokumenta, Long idDok) {
        OtpremniceEntityId id = OtpremniceEntityId.builder()
                .iDFirme(idFirme)
                .tipDokumenta(tipDokumenta)
                .iddok(idDok)
                .build();

        OtpremniceEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Otpremnica not found: IDFirme=" + idFirme + ", TipDokumenta=" + tipDokumenta + ", IDDok=" + idDok
                ));

        return mapper.toModel(entity);
    }

}
