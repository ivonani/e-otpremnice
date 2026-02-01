package org.eotpremnice.repository;

import org.eotpremnice.entity.PromenaStatusaEntity;
import org.eotpremnice.entity.id.PromenaStatusaEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 import java.util.Optional;

@Repository
public interface PromenaStatusaRepository extends JpaRepository<PromenaStatusaEntity, PromenaStatusaEntityId> {

    Optional<PromenaStatusaEntity> findByIdIdFirmeAndIdTipDokumentaAndIdIddokAndPoslato(
            String idFirme, String tipDokumenta, Integer iddok, Integer poslato
    );
}