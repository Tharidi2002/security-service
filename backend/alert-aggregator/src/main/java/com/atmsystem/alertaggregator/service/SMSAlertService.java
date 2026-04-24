package com.atmsystem.alertaggregator.service;

import com.atmsystem.alertaggregator.model.SMSAlert;
import com.atmsystem.alertaggregator.repository.SMSAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class SMSAlertService {

    private final SMSAlertRepository smsAlertRepository;
    private final WebSocketService webSocketService;

    public SMSAlert processIncomingSMS(String phoneNumber, String message, String atmId) {
        SMSAlert.Severity severity = determineSeverity(message);
        SMSAlert.AlertType alertType = determineAlertType(message);
        
        SMSAlert alert = SMSAlert.builder()
                .atmId(atmId)
                .phoneNumber(phoneNumber)
                .message(message)
                .alertType(alertType)
                .severity(severity)
                .timestamp(LocalDateTime.now())
                .processed(false)
                .bankCode(extractBankCode(atmId))
                .location(extractLocation(message))
                .build();
        
        SMSAlert savedAlert = smsAlertRepository.save(alert);
        
        // Broadcast real-time alerts
        webSocketService.broadcastNewAlert(savedAlert);
        
        if (severity == SMSAlert.Severity.CRITICAL) {
            webSocketService.broadcastCriticalAlert(savedAlert);
        }
        
        log.info("Processed SMS alert: {} - {} - {}", atmId, alertType, severity);
        return savedAlert;
    }

    public List<SMSAlert> getUnprocessedAlerts() {
        return smsAlertRepository.findByProcessed(false);
    }

    public List<SMSAlert> getCriticalAlerts() {
        return smsAlertRepository.findUnprocessedBySeverity(SMSAlert.Severity.CRITICAL);
    }

    public List<SMSAlert> getAlertsByAtmId(String atmId) {
        return smsAlertRepository.findByAtmId(atmId);
    }

    public List<SMSAlert> getAlertsByBankCode(String bankCode) {
        return smsAlertRepository.findByBankCode(bankCode);
    }

    public List<SMSAlert> getAlertsByTimeRange(LocalDateTime start, LocalDateTime end) {
        return smsAlertRepository.findByTimestampBetween(start, end);
    }

    public SMSAlert markAsProcessed(Long alertId) {
        SMSAlert alert = smsAlertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setProcessed(true);
        return smsAlertRepository.save(alert);
    }

    private SMSAlert.Severity determineSeverity(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("fire") || lowerMessage.contains("vandalism") || 
            lowerMessage.contains("tampering") || lowerMessage.contains("emergency")) {
            return SMSAlert.Severity.CRITICAL;
        } else if (lowerMessage.contains("power") || lowerMessage.contains("network") || 
                   lowerMessage.contains("door")) {
            return SMSAlert.Severity.WARNING;
        } else {
            return SMSAlert.Severity.INFO;
        }
    }

    private SMSAlert.AlertType determineAlertType(String message) {
        String lowerMessage = message.toLowerCase();
        
        if (lowerMessage.contains("door")) return SMSAlert.AlertType.DOOR_OPENING;
        if (lowerMessage.contains("fire")) return SMSAlert.AlertType.FIRE_ALARM;
        if (lowerMessage.contains("power")) return SMSAlert.AlertType.POWER_FAILURE;
        if (lowerMessage.contains("tamper")) return SMSAlert.AlertType.PHYSICAL_TAMPERING;
        if (lowerMessage.contains("network")) return SMSAlert.AlertType.NETWORK_DISCONNECT;
        if (lowerMessage.contains("cash")) return SMSAlert.AlertType.CASH_LOW;
        if (lowerMessage.contains("card")) return SMSAlert.AlertType.CARD_READER_ERROR;
        if (lowerMessage.contains("vandal")) return SMSAlert.AlertType.VANDALISM;
        
        return SMSAlert.AlertType.DOOR_OPENING; // default
    }

    private String extractBankCode(String atmId) {
        // Extract bank code from ATM ID (e.g., "BOC001" -> "BOC")
        Pattern pattern = Pattern.compile("([A-Z]+)");
        Matcher matcher = pattern.matcher(atmId);
        return matcher.find() ? matcher.group(1) : "UNKNOWN";
    }

    private String extractLocation(String message) {
        // Extract location information from SMS message
        Pattern pattern = Pattern.compile("location[:\\s]+([^.]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        return matcher.find() ? matcher.group(1).trim() : "Unknown";
    }
}
