package com.matias.incidenthub.dto;

public record IncidentStatsResponse(

        long total,

        long open,

        long inProgress,

        long resolved

) {}