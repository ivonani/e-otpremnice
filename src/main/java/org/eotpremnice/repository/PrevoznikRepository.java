package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.PrevoznikEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.PrevoznikEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrevoznikRepository extends JpaRepository<PrevoznikEntity, PrevoznikEntityId> {
}
