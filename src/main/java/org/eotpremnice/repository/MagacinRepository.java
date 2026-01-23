package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.MagacinEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.MagacinEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagacinRepository extends JpaRepository<MagacinEntity, MagacinEntityId> {
}
