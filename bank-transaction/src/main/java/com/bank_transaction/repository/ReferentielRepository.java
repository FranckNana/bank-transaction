package com.bank_transaction.repository;

import com.bank_transaction.models.entities.Referentiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferentielRepository extends JpaRepository<Referentiel, Long> {

    Optional<Referentiel> findByEventType(String eventType);
}
