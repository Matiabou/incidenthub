package com.matias.incidenthub.dto;

import com.matias.incidenthub.entity.Priority;
import com.matias.incidenthub.entity.Status;

public record IncidentUpdateRequest(

        String title,

        String description,

        Priority priority,

        Status status

) {}