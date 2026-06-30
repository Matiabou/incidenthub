package com.matias.incidenthub.repository;

import com.matias.incidenthub.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidentRepository
        extends JpaRepository<Incident, Long> {

    List<Incident> findByUser(User user);

    List<Incident> findByUserAndStatus(
            User user,
            Status status
    );

    List<Incident> findByUserAndPriority(
            User user,
            Priority priority
    );

    List<Incident> findByUserAndStatusAndPriority(
            User user,
            Status status,
            Priority priority
    );

}