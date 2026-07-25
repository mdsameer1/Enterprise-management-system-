package com.enterprise.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Business Exception
 * 
 * Base exception for business logic errors
 */
@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public BusinessException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        this(message, "BUSINESS_ERROR", httpStatus);
    }

    public BusinessException(String message) {
        this(message, "BUSINESS_ERROR", HttpStatus.BAD_REQUEST);
    }
}