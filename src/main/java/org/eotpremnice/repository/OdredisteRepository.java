package org.eotpremnice.repository;

import org.eotpremnice.entity.OdredisteEntity;
import org.eotpremnice.entity.id.OdredisteEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdredisteRepository extends JpaRepository<OdredisteEntity, OdredisteEntityId> {
}
