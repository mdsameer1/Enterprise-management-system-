package com.enterprise.exception;

import org.springframework.http.HttpStatus;

/**
 * Unauthorized Exception
 * 
 * Thrown when user is not authenticated or authorized
 */
public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException() {
        super("Unauthorized access", "UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }
}