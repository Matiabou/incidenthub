package com.matias.incidenthub.dto;

import com.matias.incidenthub.entity.Priority;
import com.matias.incidenthub.entity.Status;

import java.time.LocalDateTime;

public record IncidentResponse(

        Long id,

        String title,

        String description,

        Priority priority,

        Status status,

        LocalDateTime createdAt

) {}