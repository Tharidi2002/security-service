package com.atmsystem.securityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global Exception Handler to centralize exception handling across all controllers.
 * This class catches specific exceptions and formats a consistent, user-friendly error response.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException.
     * When a ResourceNotFoundException is thrown, this method catches it and returns a
     * 404 Not Found response with a structured error message.
     *
     * @param ex      The caught ResourceNotFoundException.
     * @param request The current web request.
     * @return A ResponseEntity containing the structured error details and a 404 status code.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles validation exceptions for @Valid annotated arguments.
     * This method is triggered when the validation of a request body fails.
     * It collects all validation error messages and returns a 400 Bad Request response.
     *
     * @param ex      The caught MethodArgumentNotValidException.
     * @param request The current web request.
     * @return A ResponseEntity containing the validation error details and a 400 status code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Validation Failed: [" + errors + "]",
                request.getDescription(false),
                "BAD_REQUEST"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other un-caught exceptions as a fallback.
     * This provides a generic 500 Internal Server Error response for any exceptions
     * not specifically handled by other @ExceptionHandler methods.
     *
     * @param ex      The caught Exception.
     * @param request The current web request.
     * @return A ResponseEntity containing generic error details and a 500 status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
