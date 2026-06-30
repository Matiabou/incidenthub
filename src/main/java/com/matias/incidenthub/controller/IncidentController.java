package com.matias.incidenthub.controller;

import com.matias.incidenthub.dto.*;

import com.matias.incidenthub.entity.Priority;
import com.matias.incidenthub.entity.Status;
import com.matias.incidenthub.service.IncidentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Incidents")
@RestController
@RequestMapping("/incidents")

@RequiredArgsConstructor

public class IncidentController {

    private final IncidentService incidentService;

    @Operation(summary = "Create a new incident")
    @PostMapping
    public IncidentResponse create(

            @RequestBody
            @Valid
            IncidentRequest request,

            Authentication auth

    ) {

        return incidentService.create(
                request,
                auth.getName()
        );

    }

    @GetMapping
    public List<IncidentResponse> getMyIncidents(

            Authentication auth,

            @RequestParam(required = false)
            Status status,

            @RequestParam(required = false)
            Priority priority

    ) {

        return incidentService.getMyIncidents(
                auth.getName(),
                status,
                priority
        );

    }

    @PutMapping("/{id}")
    public IncidentResponse update(

            @PathVariable Long id,

            @RequestBody IncidentUpdateRequest request,

            Authentication auth

    ) {

        return incidentService.update(
                id,
                request,
                auth.getName()
        );

    }

    @DeleteMapping("/{id}")
    public void delete(

            @PathVariable Long id,

            Authentication auth

    ) {

        incidentService.delete(
                id,
                auth.getName()
        );

    }

    @GetMapping("/stats")
    public IncidentStatsResponse getStats(

            Authentication auth

    ) {

        return incidentService.getStats(
                auth.getName()
        );

    }

}