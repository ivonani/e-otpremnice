package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.StavkeEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.StavkeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StavkeRepository extends JpaRepository<StavkeEntity, StavkeEntityId> {
}
