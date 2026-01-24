package org.eotpremnice.repository;

import org.eotpremnice.entity.KupacEntity;
import org.eotpremnice.entity.id.KupacEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KupacRepository extends JpaRepository<KupacEntity, KupacEntityId> {
}
