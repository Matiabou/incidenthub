package com.matias.incidenthub.dto;

import jakarta.validation.constraints.NotBlank;

import com.matias.incidenthub.entity.Priority;

public record IncidentRequest(

        @NotBlank
        String title,

        String description,

        Priority priority

) {}