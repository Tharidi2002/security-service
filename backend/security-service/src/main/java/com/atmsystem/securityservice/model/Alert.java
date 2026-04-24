package com.atmsystem.securityservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String atmId;

    @Column(nullable = false)
    private String alertType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String description;

    private String location;

    private String bankName;

    private String assignedTo;

    private String resolutionDetails;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}