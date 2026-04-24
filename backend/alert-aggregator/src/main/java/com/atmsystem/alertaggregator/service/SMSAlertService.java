package com.atmsystem.alertaggregator.service;

import com.atmsystem.alertaggregator.model.ATMLocation;
import com.atmsystem.alertaggregator.model.SMSAlert;
import com.atmsystem.alertaggregator.repository.SMSAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private final EmailService emailService;
    private final RestTemplate restTemplate;

    public SMSAlert processIncomingSMS(String phoneNumber, String message, String atmId) {
        SMSAlert.AlertType alertType = determineAlertType(message);
        SMSAlert.Severity severity = determineSeverity(message, alertType);

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

        // Send email notification
        sendEmailNotification(savedAlert);

        log.info("Processed SMS alert: {} - {} - {}", atmId, alertType, severity);
        return savedAlert;
    }

    private void sendEmailNotification(SMSAlert alert) {
        try {
            // Fetch ATM location details from auth-service
            ATMLocation atmLocation = restTemplate.getForObject("http://localhost:8081/api/atm-locations/" + alert.getAtmId(), ATMLocation.class);

            if (atmLocation != null) {
                String toEmail = getEmailForAlertType(alert.getAlertType(), atmLocation);
                if (toEmail != null && !toEmail.isEmpty()) {
                    String subject = String.format("[%s] %s Alert at %s", alert.getSeverity(), alert.getAlertType(), alert.getAtmId());
                    String body = createEmailBody(alert, atmLocation);
                    emailService.sendEmail(toEmail, subject, body);
                    log.info("Email notification sent to {} for alert ID {}", toEmail, alert.getId());
                }
            }
        } catch (Exception e) {
            log.error("Error sending email notification for alert ID {}: {}", alert.getId(), e.getMessage());
        }
    }

    private String getEmailForAlertType(SMSAlert.AlertType alertType, ATMLocation atmLocation) {
        switch (alertType) {
            case POWER_FAILURE:
            case NETWORK_DISCONNECT:
                return atmLocation.getTechnicalContactEmail();
            case FIRE_ALARM:
            case PHYSICAL_TAMPERING:
            case VANDALISM:
                return atmLocation.getSecurityContactEmail();
            case DOOR_OPENING:
            case CASH_LOW:
            case CARD_READER_ERROR:
                return atmLocation.getOperationsContactEmail();
            default:
                // For UNCATEGORIZED or other types, maybe send to a general address or the technical contact
                return atmLocation.getTechnicalContactEmail();
        }
    }

    private String createEmailBody(SMSAlert alert, ATMLocation atmLocation) {
        return String.format(
            "Dear Team,\n\nAn alert of severity '%s' has been triggered.\n\nDetails:\n" +
            "- Alert Type: %s\n" +
            "- ATM ID: %s\n" +
            "- Bank: %s\n" +
            "- Location: %s, %s\n" +
            "- Address: %s\n" +
            "- Timestamp: %s\n" +
            "- Original Message: \"%s\"\n\n" +
            "Please take the necessary action.\n\nRegards,\nATM Alert System",
            alert.getSeverity(),
            alert.getAlertType(),
            alert.getAtmId(),
            alert.getBankCode(),
            atmLocation.getCity(), atmLocation.getDistrict(),
            atmLocation.getAddress(),
            alert.getTimestamp().toString(),
            alert.getMessage()
        );
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

    private SMSAlert.Severity determineSeverity(String message, SMSAlert.AlertType alertType) {
        String lowerMessage = message.toLowerCase();

        switch (alertType) {
            case FIRE_ALARM:
            case PHYSICAL_TAMPERING:
            case VANDALISM:
                return SMSAlert.Severity.CRITICAL;
            case POWER_FAILURE:
            case NETWORK_DISCONNECT:
                return SMSAlert.Severity.WARNING;
            case DOOR_OPENING:
            case CASH_LOW:
            case CARD_READER_ERROR:
                return SMSAlert.Severity.INFO;
            default:
                if (lowerMessage.contains("emergency") || lowerMessage.contains("urgent")) {
                    return SMSAlert.Severity.CRITICAL;
                }
                return SMSAlert.Severity.INFO;
        }
    }

    private SMSAlert.AlertType determineAlertType(String message) {
        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("door")) return SMSAlert.AlertType.DOOR_OPENING;
        if (lowerMessage.contains("fire") || lowerMessage.contains("smoke")) return SMSAlert.AlertType.FIRE_ALARM;
        if (lowerMessage.contains("power") || lowerMessage.contains("outage")) return SMSAlert.AlertType.POWER_FAILURE;
        if (lowerMessage.contains("tamper") || lowerMessage.contains("shaking")) return SMSAlert.AlertType.PHYSICAL_TAMPERING;
        if (lowerMessage.contains("network") || lowerMessage.contains("disconnect")) return SMSAlert.AlertType.NETWORK_DISCONNECT;
        if (lowerMessage.contains("cash") && lowerMessage.contains("low")) return SMSAlert.AlertType.CASH_LOW;
        if (lowerMessage.contains("card reader") && (lowerMessage.contains("error") || lowerMessage.contains("fail"))) return SMSAlert.AlertType.CARD_READER_ERROR;
        if (lowerMessage.contains("vandalism") || lowerMessage.contains("damage")) return SMSAlert.AlertType.VANDALISM;

        return SMSAlert.AlertType.UNCATEGORIZED;
    }

    private String extractBankCode(String atmId) {
        Pattern pattern = Pattern.compile("([A-Z]+)");
        Matcher matcher = pattern.matcher(atmId);
        return matcher.find() ? matcher.group(1) : "UNKNOWN";
    }

    private String extractLocation(String message) {
        Pattern pattern = Pattern.compile("location[:\\s]+([^.]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        return matcher.find() ? matcher.group(1).trim() : "Unknown";
    }
}
