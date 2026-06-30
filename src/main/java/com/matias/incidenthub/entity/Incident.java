package com.matias.incidenthub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Incident {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )

    private Long id;

    @Column(
            nullable = false
    )

    private String title;

    @Column(
            columnDefinition = "TEXT"
    )

    private String description;

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            nullable = false
    )

    private Priority priority;

    @Enumerated(
            EnumType.STRING
    )

    @Column(
            nullable = false
    )

    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    @PrePersist
    public void prePersist() {
        createdAt =
                LocalDateTime.now();

        status =
                Status.OPEN;
    }

}