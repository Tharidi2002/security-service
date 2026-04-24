package com.atmsystem.securityservice.mapper;

import com.atmsystem.securityservice.dto.AlertDto;
import com.atmsystem.securityservice.model.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public AlertDto toDto(Alert alert) {
        return AlertDto.builder()
                .id(alert.getId())
                .atmId(alert.getAtmId())
                .alertType(alert.getAlertType())
                .severity(alert.getSeverity())
                .status(alert.getStatus())
                .description(alert.getDescription())
                .location(alert.getLocation())
                .bankName(alert.getBankName())
                .assignedTo(alert.getAssignedTo())
                .resolutionDetails(alert.getResolutionDetails())
                .timestamp(alert.getTimestamp())
                .build();
    }

    public Alert toEntity(AlertDto alertDto) {
        return Alert.builder()
                .id(alertDto.getId())
                .atmId(alertDto.getAtmId())
                .alertType(alertDto.getAlertType())
                .severity(alertDto.getSeverity())
                .status(alertDto.getStatus())
                .description(alertDto.getDescription())
                .location(alertDto.getLocation())
                .bankName(alertDto.getBankName())
                .assignedTo(alertDto.getAssignedTo())
                .resolutionDetails(alertDto.getResolutionDetails())
                .timestamp(alertDto.getTimestamp())
                .build();
    }
}