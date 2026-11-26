package com.runhub.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RegistrationRequest(

        @NotNull(message = "O id do atleta é obrigatório")
        UUID athleteId,

        @NotNull(message = "O id do evento é obrigatório")
        UUID eventId
) {
}