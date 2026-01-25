package org.eotpremnice.repository;

import org.eotpremnice.entity.LogEntity;
import org.eotpremnice.entity.id.LogEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, LogEntityId> {
}
