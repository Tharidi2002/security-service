package com.atmsystem.securityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for scenarios where a requested resource is not found.
 * Spring Boot will automatically respond with a 404 NOT_FOUND status code
 * whenever this exception is thrown and not caught.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for ResourceNotFoundException.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
