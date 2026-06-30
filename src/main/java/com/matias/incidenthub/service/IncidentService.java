package com.matias.incidenthub.service;

import com.matias.incidenthub.dto.*;

import com.matias.incidenthub.entity.*;

import com.matias.incidenthub.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class IncidentService {

    private final IncidentRepository incidentRepository;

    private final UserRepository userRepository;

    public IncidentResponse create(
            IncidentRequest request,
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        Incident incident =
                Incident.builder()

                        .title(request.title())

                        .description(
                                request.description()
                        )

                        .priority(
                                request.priority()
                        )

                        .user(user)

                        .build();

        Incident saved =
                incidentRepository.save(incident);

        return toResponse(saved);

    }

    public List<IncidentResponse> getMyIncidents(
            String email,
            Status status,
            Priority priority
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        List<Incident> incidents;

        if (status != null && priority != null) {

            incidents =
                    incidentRepository
                            .findByUserAndStatusAndPriority(
                                    user,
                                    status,
                                    priority
                            );

        } else if (status != null) {

            incidents =
                    incidentRepository
                            .findByUserAndStatus(
                                    user,
                                    status
                            );

        } else if (priority != null) {

            incidents =
                    incidentRepository
                            .findByUserAndPriority(
                                    user,
                                    priority
                            );

        } else {

            incidents =
                    incidentRepository
                            .findByUser(user);

        }

        return incidents
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private IncidentResponse toResponse(
            Incident incident
    ) {

        return new IncidentResponse(

                incident.getId(),

                incident.getTitle(),

                incident.getDescription(),

                incident.getPriority(),

                incident.getStatus(),

                incident.getCreatedAt()

        );

    }

    public IncidentResponse update(
            Long id,
            IncidentUpdateRequest request,
            String email
    ) {

        Incident incident =
                incidentRepository
                        .findById(id)
                        .orElseThrow();

        if (!incident.getUser()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "Unauthorized"
            );

        }

        if (request.title() != null) {
            incident.setTitle(
                    request.title()
            );
        }

        if (request.description() != null) {
            incident.setDescription(
                    request.description()
            );
        }

        if (request.priority() != null) {
            incident.setPriority(
                    request.priority()
            );
        }

        if (request.status() != null) {
            incident.setStatus(
                    request.status()
            );
        }

        Incident saved =
                incidentRepository.save(
                        incident
                );

        return toResponse(saved);
    }

    public void delete(
            Long id,
            String email
    ) {

        Incident incident =
                incidentRepository
                        .findById(id)
                        .orElseThrow();

        if (!incident.getUser()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "Unauthorized"
            );

        }

        incidentRepository.delete(
                incident
        );
    }

    public IncidentStatsResponse getStats(
            String email
    ) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        List<Incident> incidents =
                incidentRepository
                        .findByUser(user);

        long total =
                incidents.size();

        long open =
                incidents.stream()
                        .filter(i ->
                                i.getStatus()
                                        == Status.OPEN
                        )
                        .count();

        long inProgress =
                incidents.stream()
                        .filter(i ->
                                i.getStatus()
                                        == Status.IN_PROGRESS
                        )
                        .count();

        long resolved =
                incidents.stream()
                        .filter(i ->
                                i.getStatus()
                                        == Status.RESOLVED
                        )
                        .count();

        return new IncidentStatsResponse(
                total,
                open,
                inProgress,
                resolved
        );
    }

}