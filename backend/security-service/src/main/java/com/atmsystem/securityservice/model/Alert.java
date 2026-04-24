package com.atmsystem.securityservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String atmId;
    private String alertType; // ex: FRAUD, VANDALISM
    private String message;
    private LocalDateTime timestamp;
}