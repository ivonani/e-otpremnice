package org.eotpremnice.repository;

import org.eotpremnice.entity.SemaforEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemaforRepository extends JpaRepository<SemaforEntity, String> {
    Optional<SemaforEntity> findByIdRacunar(String idRacunar);
}