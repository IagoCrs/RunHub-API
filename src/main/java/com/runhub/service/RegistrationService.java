package com.runhub.service;

import com.runhub.dto.RegistrationRequest;
import com.runhub.dto.RegistrationResponse;
import com.runhub.dto.RegistrationUpdateRequest;
import com.runhub.enums.EventStatus;
import com.runhub.exception.ApiException;
import com.runhub.model.Athlete;
import com.runhub.model.Event;
import com.runhub.model.Registration;
import com.runhub.repository.AthleteRepository;
import com.runhub.repository.EventRepository;
import com.runhub.repository.RegistrationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final AthleteRepository athleteRepository;
    private final EventRepository eventRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               AthleteRepository athleteRepository,
                               EventRepository eventRepository) {
        this.registrationRepository = registrationRepository;
        this.athleteRepository = athleteRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public RegistrationResponse create(RegistrationRequest request) {
        Athlete athlete = athleteRepository.findById(request.athleteId())
                .orElseThrow(() -> ApiException.notFound("Atleta não encontrado"));
        Event event = eventRepository.findById(request.eventId())
                .orElseThrow(() -> ApiException.notFound("Evento não encontrado"));

        if (registrationRepository.existsByAthleteIdAndEventId(athlete.getId(), event.getId())) {
            throw ApiException.conflict("O atleta já está inscrito neste evento");
        }

        if (event.getStatus() != EventStatus.OPEN) {
            throw ApiException.unprocessable("O evento não está aberto para inscrições");
        }

        long inscritos = registrationRepository.countByEventId(event.getId());
        if (inscritos >= event.getMaxParticipants()) {
            throw ApiException.unprocessable("O evento está lotado");
        }

        Integer ultimoNumero = registrationRepository.findMaxBibNumberByEventId(event.getId());
        int proximoNumero = (ultimoNumero == null) ? 1 : ultimoNumero + 1;

        Registration registration = new Registration();
        registration.setAthlete(athlete);
        registration.setEvent(event);
        registration.setBibNumber(proximoNumero);

        return RegistrationResponse.from(registrationRepository.save(registration));
    }

    @Transactional(readOnly = true)
    public RegistrationResponse findById(UUID id) {
        return RegistrationResponse.from(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public Page<RegistrationResponse> findAll(UUID eventId, Pageable pageable) {
        Page<Registration> pagina;
        if (eventId == null) {
            pagina = registrationRepository.findAll(pageable);
        } else {
            pagina = registrationRepository.findByEventId(eventId, pageable);
        }
        return pagina.map(RegistrationResponse::from);
    }

    @Transactional
    public RegistrationResponse update(UUID id, RegistrationUpdateRequest request) {
        Registration registration = buscarPorId(id);
        registration.setStatus(request.status());
        registration.setFinishTime(request.finishTime());
        return RegistrationResponse.from(registrationRepository.save(registration));
    }

    @Transactional
    public void delete(UUID id) {
        Registration registration = buscarPorId(id);
        registrationRepository.delete(registration);
    }

    private Registration buscarPorId(UUID id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Inscrição não encontrada"));
    }
}