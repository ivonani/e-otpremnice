package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.LogEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.LogEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, LogEntityId> {
}
