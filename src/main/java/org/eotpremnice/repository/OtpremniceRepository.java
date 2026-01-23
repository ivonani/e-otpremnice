package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.OtpremniceEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.OtpremniceEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpremniceRepository extends JpaRepository<OtpremniceEntity, OtpremniceEntityId> {
}
