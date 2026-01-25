package org.eotpremnice.repository;

import org.eotpremnice.entity.StavkeEntity;
import org.eotpremnice.entity.id.StavkeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StavkeRepository extends JpaRepository<StavkeEntity, StavkeEntityId> {

    List<StavkeEntity> findByIdIDFirmeAndIdTipDokumentaAndIdIddok(
            String idFirme, String tipDokumenta, Long iddok
    );
}
