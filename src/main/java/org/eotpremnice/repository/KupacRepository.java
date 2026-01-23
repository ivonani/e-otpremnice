package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.KupacEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.KupacEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KupacRepository extends JpaRepository<KupacEntity, KupacEntityId> {
}
