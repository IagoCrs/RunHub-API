package com.runhub.dto;

import com.runhub.enums.RegistrationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;

public record RegistrationUpdateRequest(

        @NotNull(message = "O status é obrigatório")
        RegistrationStatus status,

        Duration finishTime
) {
}