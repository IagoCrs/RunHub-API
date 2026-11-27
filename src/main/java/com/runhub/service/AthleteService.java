package com.runhub.service;

import com.runhub.dto.AthleteRequest;
import com.runhub.dto.AthleteResponse;
import com.runhub.exception.ApiException;
import com.runhub.model.Athlete;
import com.runhub.repository.AthleteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AthleteService {

    private final AthleteRepository athleteRepository;

    public AthleteService(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Transactional
    public AthleteResponse create(AthleteRequest request) {
        if (athleteRepository.existsByEmail(request.email())) {
            throw ApiException.conflict("Já existe um atleta cadastrado com este e-mail");
        }

        Athlete athlete = new Athlete();
        athlete.setName(request.name());
        athlete.setEmail(request.email());
        athlete.setBirthDate(request.birthDate());
        athlete.setCategory(request.category());
        athlete.setAveragePace(request.averagePace());

        return AthleteResponse.from(athleteRepository.save(athlete));
    }

    @Transactional(readOnly = true)
    public AthleteResponse findById(UUID id) {
        return AthleteResponse.from(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public List<AthleteResponse> findAll() {
        return athleteRepository.findAll().stream()
                .map(AthleteResponse::from)
                .toList();
    }

    @Transactional
    public AthleteResponse update(UUID id, AthleteRequest request) {
        Athlete athlete = buscarPorId(id);

        if (!athlete.getEmail().equalsIgnoreCase(request.email()) && athleteRepository.existsByEmail(request.email())) {
            throw ApiException.conflict("Já existe um atleta cadastrado com este e-mail");
        }

        athlete.setName(request.name());
        athlete.setEmail(request.email());
        athlete.setBirthDate(request.birthDate());
        athlete.setCategory(request.category());
        athlete.setAveragePace(request.averagePace());

        return AthleteResponse.from(athleteRepository.save(athlete));
    }

    @Transactional
    public void delete(UUID id) {
        Athlete athlete = buscarPorId(id);
        athleteRepository.delete(athlete);
    }

    private Athlete buscarPorId(UUID id) {
        return athleteRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Atleta não encontrado"));
    }
}
