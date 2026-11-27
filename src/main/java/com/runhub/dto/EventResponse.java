package com.runhub.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.runhub.enums.EventStatus;
import com.runhub.model.Event;

public record EventResponse(
	UUID id,
	String title,
	String city,
	LocalDateTime eventDate,
	Double distanceKm,
	Integer maxParticipants,
	EventStatus status,
	LocalDateTime createdAt
) {
	public static EventResponse from(Event event) {
		return new EventResponse(
			event.getId(),
			event.getTitle(),
			event.getCity(),
			event.getEventDate(),
			event.getDistanceKm(),
			event.getMaxParticipants(),
			event.getStatus(),
			event.getCreatedAt()
		);
	}
}
