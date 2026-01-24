package org.eotpremnice.repository;

import org.eotpremnice.entity.PrevoznikEntity;
import org.eotpremnice.entity.id.PrevoznikEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrevoznikRepository extends JpaRepository<PrevoznikEntity, PrevoznikEntityId> {
}
