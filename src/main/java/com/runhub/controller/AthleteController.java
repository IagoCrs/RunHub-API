package com.runhub.controller;

import com.runhub.dto.AthleteRequest;
import com.runhub.dto.AthleteResponse;
import com.runhub.service.AthleteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/athletes")
public class AthleteController {

    private final AthleteService athleteService;

    public AthleteController(AthleteService athleteService) {
        this.athleteService = athleteService;
    }

    @PostMapping
    public ResponseEntity<AthleteResponse> create(@Valid @RequestBody AthleteRequest request) {
        AthleteResponse response = athleteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(athleteService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AthleteResponse>> findAll() {
        return ResponseEntity.ok(athleteService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AthleteResponse> update(@PathVariable UUID id,
                                                  @Valid @RequestBody AthleteRequest request) {
        return ResponseEntity.ok(athleteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        athleteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
