package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.VozacEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.VozacEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VozacRepository extends JpaRepository<VozacEntity, VozacEntityId> {
}
