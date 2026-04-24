package com.atmsystem.alertaggregator.controller;

import com.atmsystem.alertaggregator.model.SMSAlert;
import com.atmsystem.alertaggregator.service.SMSAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sms-alerts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SMSAlertController {

    private final SMSAlertService smsAlertService;

    @PostMapping("/receive")
    public ResponseEntity<SMSAlert> receiveSMS(@RequestBody Map<String, String> smsRequest) {
        SMSAlert alert = smsAlertService.processIncomingSMS(
            smsRequest.get("phoneNumber"),
            smsRequest.get("message"),
            smsRequest.get("atmId")
        );
        return ResponseEntity.ok(alert);
    }

    @GetMapping("/unprocessed")
    public ResponseEntity<List<SMSAlert>> getUnprocessedAlerts() {
        return ResponseEntity.ok(smsAlertService.getUnprocessedAlerts());
    }

    @GetMapping("/critical")
    public ResponseEntity<List<SMSAlert>> getCriticalAlerts() {
        return ResponseEntity.ok(smsAlertService.getCriticalAlerts());
    }

    @GetMapping("/atm/{atmId}")
    public ResponseEntity<List<SMSAlert>> getAlertsByAtmId(@PathVariable String atmId) {
        return ResponseEntity.ok(smsAlertService.getAlertsByAtmId(atmId));
    }

    @GetMapping("/bank/{bankCode}")
    public ResponseEntity<List<SMSAlert>> getAlertsByBankCode(@PathVariable String bankCode) {
        return ResponseEntity.ok(smsAlertService.getAlertsByBankCode(bankCode));
    }

    @GetMapping("/time-range")
    public ResponseEntity<List<SMSAlert>> getAlertsByTimeRange(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return ResponseEntity.ok(smsAlertService.getAlertsByTimeRange(startTime, endTime));
    }

    @PutMapping("/{alertId}/process")
    public ResponseEntity<SMSAlert> markAsProcessed(@PathVariable Long alertId) {
        return ResponseEntity.ok(smsAlertService.markAsProcessed(alertId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        List<SMSAlert> unprocessed = smsAlertService.getUnprocessedAlerts();
        List<SMSAlert> critical = smsAlertService.getCriticalAlerts();
        
        long criticalCount = critical.size();
        long warningCount = unprocessed.stream()
                .filter(a -> a.getSeverity() == SMSAlert.Severity.WARNING)
                .count();
        long infoCount = unprocessed.stream()
                .filter(a -> a.getSeverity() == SMSAlert.Severity.INFO)
                .count();

        return ResponseEntity.ok(Map.of(
            "totalUnprocessed", unprocessed.size(),
            "criticalCount", criticalCount,
            "warningCount", warningCount,
            "infoCount", infoCount,
            "recentAlerts", unprocessed.stream().limit(10).toList()
        ));
    }
}
