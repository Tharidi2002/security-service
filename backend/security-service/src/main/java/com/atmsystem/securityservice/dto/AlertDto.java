package com.atmsystem.securityservice.dto;

import com.atmsystem.securityservice.model.Severity;
import com.atmsystem.securityservice.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDto {
    private Long id;

    @NotBlank(message = "ATM ID cannot be blank")
    private String atmId;

    private String alertType;

    @NotNull(message = "Severity cannot be null")
    private Severity severity;

    @NotNull(message = "Status cannot be null")
    private Status status;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String location;

    private String bankName;

    private String assignedTo;

    private String resolutionDetails;

    private LocalDateTime timestamp;
}
