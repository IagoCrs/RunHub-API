package com.runhub.dto;

import com.runhub.model.Athlete;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AthleteResponse(
        UUID id,
        String name,
        String email,
        LocalDate birthDate,
        String category,
        Double averagePace,
        LocalDateTime createdAt
) {

    public static AthleteResponse from(Athlete athlete) {
        return new AthleteResponse(
                athlete.getId(),
                athlete.getName(),
                athlete.getEmail(),
                athlete.getBirthDate(),
                athlete.getCategory(),
                athlete.getAveragePace(),
                athlete.getCreatedAt()
        );
    }
}
