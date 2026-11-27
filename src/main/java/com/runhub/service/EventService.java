package com.runhub.service;

import com.runhub.dto.EventRequest;
import com.runhub.dto.EventResponse;
import com.runhub.exception.ApiException;
import com.runhub.model.Event;
import com.runhub.repository.EventRepository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	private final EventRepository eventRepository;

	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public EventResponse create(EventRequest request) {
		Event event = new Event();
		event.setTitle(request.title());
		event.setCity(request.city());
		event.setEventDate(request.eventDate());
		event.setDistanceKm(request.distanceKm());
		event.setMaxParticipants(request.maxParticipants());

		Event savedEvent = eventRepository.save(event);
		return EventResponse.from(savedEvent);
	}

	public Page<EventResponse> findAll(String city, Pageable pageable) {
		Page<Event> events;
		
		if (city == null || city.isBlank()) {
			events = eventRepository.findAll(pageable);
		} else {
			events = eventRepository.findByCityContainingIgnoreCase(city.trim(), pageable);
		}

		return events.map(EventResponse::from);
	}

	public EventResponse update(UUID id, EventRequest request) {
		Event event = buscarPorId(id);

		event.setCity(request.city());
		event.setTitle(request.title());
		event.setEventDate(request.eventDate());
		event.setDistanceKm(request.distanceKm());
		event.setMaxParticipants(request.maxParticipants());

		Event savedEvent = eventRepository.save(event);
		return EventResponse.from(savedEvent);
	}

	public void delete(UUID id) {
		Event event = buscarPorId(id);
		eventRepository.delete(event);
	}

	public EventResponse findById(UUID id) {
		return EventResponse.from(buscarPorId(id));
	}

	private Event buscarPorId(UUID id) {
		return eventRepository.findById(id).orElseThrow(() -> ApiException.notFound("Evento não encontrado"));
	}
}