package com.atmsystem.authservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atm_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ATMLocation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String atmId;
    
    @Column(nullable = false)
    private String bankCode;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String technicalContactEmail;

    @Column(nullable = false)
    private String securityContactEmail;

    @Column(nullable = false)
    private String operationsContactEmail;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String district;
    
    private String latitude;
    private String longitude;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime lastMaintenance;
    
    private String notes;
    
    public enum Status {
        ACTIVE, INACTIVE, UNDER_MAINTENANCE, DECOMMISSIONED
    }
}
