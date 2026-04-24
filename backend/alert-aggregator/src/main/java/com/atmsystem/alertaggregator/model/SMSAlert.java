package com.atmsystem.alertaggregator.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sms_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SMSAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String atmId;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(nullable = false)
    private boolean processed = false;
    
    private String bankCode;
    private String location;
    
    public enum AlertType {
        DOOR_OPENING, FIRE_ALARM, POWER_FAILURE, PHYSICAL_TAMPERING, 
        NETWORK_DISCONNECT, CASH_LOW, CARD_READER_ERROR, VANDALISM, UNCATEGORIZED
    }
    
    public enum Severity {
        CRITICAL, WARNING, INFO
    }
}
