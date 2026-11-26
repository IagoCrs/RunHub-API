package com.runhub.repository;

import com.runhub.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    boolean existsByAthleteIdAndEventId(UUID athleteId, UUID eventId);

    long countByEventId(UUID eventId);

    Page<Registration> findByEventId(UUID eventId, Pageable pageable);

    @Query("select max(r.bibNumber) from Registration r where r.event.id = :eventId")
    Integer findMaxBibNumberByEventId(@Param("eventId") UUID eventId);
}