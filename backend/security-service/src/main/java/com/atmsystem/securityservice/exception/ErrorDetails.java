package com.atmsystem.securityservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A data class representing the structured error response body.
 * This is used by the GlobalExceptionHandler to provide consistent error messages.
 */
@Data
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;
}
