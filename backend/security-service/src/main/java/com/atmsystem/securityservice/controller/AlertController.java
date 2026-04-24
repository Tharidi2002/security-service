package com.atmsystem.securityservice.controller;

import com.atmsystem.securityservice.model.Alert;
import com.atmsystem.securityservice.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        alert.setTimestamp(java.time.LocalDateTime.now());
        return alertRepository.save(alert);
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}