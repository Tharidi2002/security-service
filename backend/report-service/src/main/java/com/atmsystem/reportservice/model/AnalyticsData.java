package com.atmsystem.reportservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String bankCode;
    
    @Column(nullable = false)
    private String atmId;
    
    @Column(nullable = false)
    private String reportType;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportData;
    
    @Column(nullable = false)
    private LocalDateTime generatedAt;
    
    @Column(nullable = false)
    private LocalDateTime periodStart;
    
    @Column(nullable = false)
    private LocalDateTime periodEnd;
    
    private Long totalAlerts;
    private Long criticalAlerts;
    private Long warningAlerts;
    private Long infoAlerts;
    
    public enum ReportType {
        DAILY, WEEKLY, MONTHLY, CUSTOM
    }
}
