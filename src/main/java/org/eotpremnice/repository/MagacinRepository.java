package org.eotpremnice.repository;

import org.eotpremnice.entity.MagacinEntity;
import org.eotpremnice.entity.id.MagacinEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagacinRepository extends JpaRepository<MagacinEntity, MagacinEntityId> {
}
