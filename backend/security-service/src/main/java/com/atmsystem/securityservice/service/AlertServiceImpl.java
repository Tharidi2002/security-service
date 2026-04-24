package com.atmsystem.securityservice.service;

import com.atmsystem.securityservice.dto.AlertDto;
import com.atmsystem.securityservice.exception.ResourceNotFoundException;
import com.atmsystem.securityservice.mapper.AlertMapper;
import com.atmsystem.securityservice.model.Alert;
import com.atmsystem.securityservice.model.Severity;
import com.atmsystem.securityservice.model.Status;
import com.atmsystem.securityservice.repository.AlertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for handling business logic related to security alerts.
 * This class interacts with the AlertRepository and uses AlertMapper for DTO conversions.
 */
@Service
@AllArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final AlertMapper alertMapper;

    /**
     * Creates a new alert.
     *
     * @param alertDto The DTO containing the details for the new alert.
     * @return The DTO of the newly created alert.
     */
    @Override
    @Transactional
    public AlertDto createAlert(AlertDto alertDto) {
        Alert alert = alertMapper.toEntity(alertDto);
        Alert savedAlert = alertRepository.save(alert);
        return alertMapper.toDto(savedAlert);
    }

    /**
     * Retrieves an alert by its unique ID.
     *
     * @param id The ID of the alert.
     * @return The AlertDto if found.
     * @throws ResourceNotFoundException if no alert is found with the given ID.
     */
    @Override
    @Transactional(readOnly = true)
    public AlertDto getAlertById(Long id) {
        return alertRepository.findById(id)
                .map(alertMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
    }

    /**
     * Retrieves all alerts from the system.
     *
     * @return A list of all AlertDtos.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlertDto> getAllAlerts() {
        return alertRepository.findAll().stream()
                .map(alertMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status and resolution details of a specific alert.
     *
     * @param id                The ID of the alert to update.
     * @param status            The new status for the alert.
     * @param resolutionDetails Details describing the resolution of the alert.
     * @return The updated AlertDto.
     * @throws ResourceNotFoundException if no alert is found with the given ID.
     */
    @Override
    @Transactional
    public AlertDto updateAlertStatus(Long id, Status status, String resolutionDetails) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
        alert.setStatus(status);
        alert.setResolutionDetails(resolutionDetails);
        Alert updatedAlert = alertRepository.save(alert);
        return alertMapper.toDto(updatedAlert);
    }

    /**
     * Deletes an alert by its ID.
     *
     * @param id The ID of the alert to be deleted.
     * @throws ResourceNotFoundException if no alert is found with the given ID.
     */
    @Override
    @Transactional
    public void deleteAlert(Long id) {
        if (!alertRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alert not found with id: " + id);
        }
        alertRepository.deleteById(id);
    }

    /**
     * Finds all alerts associated with a specific ATM ID.
     *
     * @param atmId The ATM ID to search by.
     * @return A list of AlertDtos associated with the given ATM ID.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlertDto> getAlertsByAtmId(String atmId) {
        return alertRepository.findByAtmId(atmId).stream()
                .map(alertMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds all alerts with a specific severity level.
     *
     * @param severity The severity level to search by.
     * @return A list of AlertDtos with the specified severity.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlertDto> getAlertsBySeverity(Severity severity) {
        return alertRepository.findBySeverity(severity).stream()
                .map(alertMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds all alerts with a specific status.
     *
     * @param status The status to search by.
     * @return A list of AlertDtos with the specified status.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlertDto> getAlertsByStatus(Status status) {
        return alertRepository.findByStatus(status).stream()
                .map(alertMapper::toDto)
                .collect(Collectors.toList());
    }
}
