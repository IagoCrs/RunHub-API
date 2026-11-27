package com.runhub.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EventRequest(
	@NotBlank(message = "O título é obrigatório")
	String title,

	@NotBlank(message = "A cidade é obrigatória")
	String city,

	@NotNull(message = "A data do evento é obrigatória")
	LocalDateTime eventDate,

	@NotNull(message = "A distância é obrigatória")
	@Positive(message = "A distância precisa ser maior que zero")
	Double distanceKm,

	@NotNull(message = "O limite de participantes é obrigatório")
	@Positive(message = "O limite de participantes deve ser de pelo menos 1")
	Integer maxParticipants	
) {

}