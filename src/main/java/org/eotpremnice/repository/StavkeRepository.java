package org.eotpremnice.repository;

import org.eotpremnice.entity.StavkeEntity;
import org.eotpremnice.entity.id.StavkeEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StavkeRepository extends JpaRepository<StavkeEntity, StavkeEntityId> {
}
