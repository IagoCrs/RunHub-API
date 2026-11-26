package com.runhub.dto;

import com.runhub.enums.RegistrationStatus;
import com.runhub.model.Registration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationResponse(
        UUID id,
        UUID athleteId,
        String athleteName,
        UUID eventId,
        String eventTitle,
        Integer bibNumber,
        RegistrationStatus status,
        Duration finishTime,
        LocalDateTime registeredAt
) {

    public static RegistrationResponse from(Registration registration) {
        return new RegistrationResponse(
                registration.getId(),
                registration.getAthlete().getId(),
                registration.getAthlete().getName(),
                registration.getEvent().getId(),
                registration.getEvent().getTitle(),
                registration.getBibNumber(),
                registration.getStatus(),
                registration.getFinishTime(),
                registration.getRegisteredAt()
        );
    }
}