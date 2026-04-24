package com.atmsystem.alertaggregator.service;

import com.atmsystem.alertaggregator.model.SMSAlert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastNewAlert(SMSAlert alert) {
        try {
            messagingTemplate.convertAndSend("/topic/alerts", alert);
            log.info("Broadcasted new alert: {}", alert.getId());
        } catch (Exception e) {
            log.error("Failed to broadcast alert", e);
        }
    }

    public void broadcastCriticalAlert(SMSAlert alert) {
        try {
            messagingTemplate.convertAndSend("/topic/critical-alerts", alert);
            log.info("Broadcasted critical alert: {}", alert.getId());
        } catch (Exception e) {
            log.error("Failed to broadcast critical alert", e);
        }
    }

    public void broadcastDashboardUpdate(Object dashboardData) {
        try {
            messagingTemplate.convertAndSend("/topic/dashboard", dashboardData);
            log.info("Broadcasted dashboard update");
        } catch (Exception e) {
            log.error("Failed to broadcast dashboard update", e);
        }
    }
}
