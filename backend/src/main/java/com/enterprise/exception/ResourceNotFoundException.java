package com.enterprise.exception;

import org.springframework.http.HttpStatus;

/**
 * Resource Not Found Exception
 * 
 * Thrown when a requested resource is not found
 */
public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s not found with id: %d", resource, id),
                "RESOURCE_NOT_FOUND",
                HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found with %s: %s", resource, field, value),
                "RESOURCE_NOT_FOUND",
                HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND);
    }
}