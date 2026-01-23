package org.eotpremnice.repository;

import org.eotpremnice.entity.FirmaEntity;
import org.eotpremnice.entity.IsporukaEntity;
import org.eotpremnice.entity.id.FirmEntityId;
import org.eotpremnice.entity.id.IsporukaEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IsporukaRepository extends JpaRepository<IsporukaEntity, IsporukaEntityId> {
}
