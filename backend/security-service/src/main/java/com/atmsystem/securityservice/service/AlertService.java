package com.atmsystem.securityservice.service;

import com.atmsystem.securityservice.dto.AlertDto;
import com.atmsystem.securityservice.model.Severity;
import com.atmsystem.securityservice.model.Status;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the security alert service.
 * Defines the business logic operations for managing alerts.
 */
public interface AlertService {

    /**
     * Creates a new alert.
     *
     * @param alertDto The DTO for the alert to be created.
     * @return The created alert as a DTO.
     */
    AlertDto createAlert(AlertDto alertDto);

    /**
     * Retrieves an alert by its ID.
     *
     * @param id The ID of the alert to retrieve.
     * @return An Optional containing the alert DTO if found, otherwise empty.
     */
    Optional<AlertDto> getAlertById(Long id);

    /**
     * Retrieves all alerts.
     *
     * @return A list of all alert DTOs.
     */
    List<AlertDto> getAllAlerts();

    /**
     * Updates the status of an existing alert.
     *
     * @param id                The ID of the alert to update.
     * @param status            The new status.
     * @param resolutionDetails The details of the resolution.
     * @return The updated alert DTO.
     */
    AlertDto updateAlertStatus(Long id, Status status, String resolutionDetails);

    /**
     * Deletes an alert by its ID.
     *
     * @param id The ID of the alert to delete.
     */
    void deleteAlert(Long id);

    /**
     * Retrieves all alerts for a specific ATM.
     *
     * @param atmId The ID of the ATM.
     * @return A list of alert DTOs for the specified ATM.
     */
    List<AlertDto> getAlertsByAtmId(String atmId);

    /**
     * Retrieves all alerts with a specific severity level.
     *
     * @param severity The severity level.
     * @return A list of alert DTOs with the specified severity.
     */
    List<AlertDto> getAlertsBySeverity(Severity severity);

    /**
     * Retrieves all alerts with a specific status.
     *
     * @param status The status.
     * @return A list of alert DTOs with the specified status.
     */
    List<AlertDto> getAlertsByStatus(Status status);
}
