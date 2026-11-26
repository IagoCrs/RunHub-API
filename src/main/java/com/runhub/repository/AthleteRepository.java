package com.runhub.repository;

import com.runhub.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AthleteRepository extends JpaRepository<Athlete, UUID> {

    boolean existsByEmail(String email);
}