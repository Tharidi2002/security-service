package com.atmsystem.securityservice.controller;

import com.atmsystem.securityservice.dto.AlertDto;
import com.atmsystem.securityservice.model.Severity;
import com.atmsystem.securityservice.model.Status;
import com.atmsystem.securityservice.service.AlertService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing security alerts.
 * Provides endpoints for creating, retrieving, updating, and deleting alerts.
 */
@RestController
@RequestMapping("/api/alerts")
@AllArgsConstructor
public class AlertController {

    private final AlertService alertService;

    /**
     * Creates a new security alert.
     * The @Valid annotation triggers the validation of the AlertDto object.
     *
     * @param alertDto The alert data transfer object containing the details of the alert.
     * @return A ResponseEntity containing the created alert and a CREATED status.
     */
    @PostMapping
    public ResponseEntity<AlertDto> createAlert(@Valid @RequestBody AlertDto alertDto) {
        return new ResponseEntity<>(alertService.createAlert(alertDto), HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific alert by its ID.
     *
     * @param id The ID of the alert to retrieve.
     * @return A ResponseEntity containing the alert. If not found, the GlobalExceptionHandler will
     *         return a 404 Not Found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AlertDto> getAlertById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlertById(id));
    }

    /**
     * Retrieves all security alerts.
     *
     * @return A list of all alert data transfer objects.
     */
    @GetMapping
    public List<AlertDto> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    /**
     * Updates the status and resolution details of an existing alert.
     *
     * @param id                The ID of the alert to update.
     * @param status            The new status of the alert.
     * @param resolutionDetails The details about the resolution.
     * @return A ResponseEntity containing the updated alert.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlertDto> updateAlertStatus(@PathVariable Long id, @RequestParam Status status, @RequestParam String resolutionDetails) {
        return ResponseEntity.ok(alertService.updateAlertStatus(id, status, resolutionDetails));
    }

    /**
     * Deletes an alert by its ID.
     *
     * @param id The ID of the alert to delete.
     * @return A ResponseEntity with a NO_CONTENT status indicating successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all alerts associated with a specific ATM ID.
     *
     * @param atmId The ATM ID to search for.
     * @return A list of alerts for the given ATM ID.
     */
    @GetMapping("/atm/{atmId}")
    public List<AlertDto> getAlertsByAtmId(@PathVariable String atmId) {
        return alertService.getAlertsByAtmId(atmId);
    }

    /**
     * Retrieves all alerts of a specific severity level.
     *
     * @param severity The severity level (e.g., HIGH, MEDIUM, LOW).
     * @return A list of alerts with the specified severity.
     */
    @GetMapping("/severity/{severity}")
    public List<AlertDto> getAlertsBySeverity(@PathVariable Severity severity) {
        return alertService.getAlertsBySeverity(severity);
    }

    /**
     * Retrieves all alerts with a specific status.
     *
     * @param status The status of the alerts (e.g., OPEN, IN_PROGRESS, CLOSED).
     * @return A list of alerts with the specified status.
     */
    @GetMapping("/status/{status}")
    public List<AlertDto> getAlertsByStatus(@PathVariable Status status) {
        return alertService.getAlertsByStatus(status);
    }
}
