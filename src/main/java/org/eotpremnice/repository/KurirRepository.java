package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.KurirEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.KurirEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KurirRepository extends JpaRepository<KurirEntity, KurirEntityId> {
}
