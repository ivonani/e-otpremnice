package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmaRepository extends JpaRepository<FirmaEntity, FirmEntityId> {
}
