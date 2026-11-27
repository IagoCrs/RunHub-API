package com.runhub.controller;

import com.runhub.dto.EventRequest;
import com.runhub.dto.EventResponse;
import com.runhub.service.EventService;

import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {
	@PostMapping
	public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
		EventResponse response = eventService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventResponse> findById(@PathVariable UUID id) {
		return ResponseEntity.ok(eventService.findById(id));
	}

	@GetMapping
	public ResponseEntity<Page<EventResponse>> findAll(
		@RequestParam(required = false) String city,
		@PageableDefault(size = 10, sort = "eventDate") Pageable pageable) {
		return ResponseEntity.ok(eventService.findAll(city, pageable));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EventResponse> update(@PathVariable UUID id, @Valid @RequestBody EventRequest request) {
		return ResponseEntity.ok(eventService.update(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		eventService.delete(id);
		return ResponseEntity.noContent().build();
	}

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
}
