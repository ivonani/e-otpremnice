package org.eotpremnice.repository;

import org.eotpremnice.entity.DokumentPDFEntity;
import org.eotpremnice.entity.id.DokumentPDFEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DokumentPdfRepository extends JpaRepository<DokumentPDFEntity, DokumentPDFEntityId> {

    List<DokumentPDFEntity> findByIdFirmeAndTipDokumentaAndIddok(
            String idFirme, String tipDokumenta, Long iddok
    );
}
