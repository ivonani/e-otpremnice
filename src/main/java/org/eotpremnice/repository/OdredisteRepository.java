package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.OdredisteEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.OdredisteEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OdredisteRepository extends JpaRepository<OdredisteEntity, OdredisteEntityId> {
}
