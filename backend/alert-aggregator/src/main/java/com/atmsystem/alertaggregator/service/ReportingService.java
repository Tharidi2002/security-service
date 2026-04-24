package com.atmsystem.alertaggregator.service;

import com.atmsystem.alertaggregator.model.SMSAlert;
import com.atmsystem.alertaggregator.repository.SMSAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingService {

    private final SMSAlertRepository alertRepository;
    private final EmailService emailService;

    @Value("${reporting.email.to}")
    private String reportingEmailTo;

    public void generateAndSendDailyReport() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        List<SMSAlert> alerts = alertRepository.findByTimestampAfter(yesterday);

        if (alerts.isEmpty()) {
            log.info("No alerts in the last 24 hours. No report to generate.");
            return;
        }

        String report = generateReportText(alerts);
        String subject = "Daily ATM Alert Summary - " + LocalDateTime.now().toLocalDate();

        emailService.sendEmail(reportingEmailTo, subject, report);
        log.info("Daily report sent to {}", reportingEmailTo);
    }

    private String generateReportText(List<SMSAlert> alerts) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear Manager,\n\nHere is the summary of ATM alerts from the last 24 hours.\n\n");

        sb.append("Total Alerts: ").append(alerts.size()).append("\n\n");

        Map<SMSAlert.AlertType, Long> alertsByType = alerts.stream()
                .collect(Collectors.groupingBy(SMSAlert::getAlertType, Collectors.counting()));

        sb.append("Alerts by Type:\n");
        alertsByType.forEach((type, count) -> 
            sb.append("- ").append(type).append(": ").append(count).append("\n")
        );

        sb.append("\nAlerts by Severity:\n");
        Map<SMSAlert.Severity, Long> alertsBySeverity = alerts.stream()
                .collect(Collectors.groupingBy(SMSAlert::getSeverity, Collectors.counting()));
        alertsBySeverity.forEach((severity, count) -> 
            sb.append("- ").append(severity).append(": ").append(count).append("\n")
        );
        
        sb.append("\nThis is an automated report.\n\nRegards,\nATM Alert System");

        return sb.toString();
    }
}
