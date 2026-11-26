package com.runhub.controller;

import com.runhub.dto.RegistrationRequest;
import com.runhub.dto.RegistrationResponse;
import com.runhub.dto.RegistrationUpdateRequest;
import com.runhub.service.RegistrationService;
import jakarta.validation.Valid;
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

import java.util.UUID;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> create(@Valid @RequestBody RegistrationRequest request) {
        RegistrationResponse response = registrationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(registrationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RegistrationResponse>> findAll(
            @RequestParam(required = false) UUID eventId,
            @PageableDefault(size = 10, sort = "registeredAt") Pageable pageable) {
        return ResponseEntity.ok(registrationService.findAll(eventId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistrationResponse> update(@PathVariable UUID id,
                                                       @Valid @RequestBody RegistrationUpdateRequest request) {
        return ResponseEntity.ok(registrationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}