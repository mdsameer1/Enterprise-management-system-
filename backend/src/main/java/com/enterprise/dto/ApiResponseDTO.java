package com.enterprise.dto;

import lombok.*;

/**
 * API Response DTO
 * Generic wrapper for API responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseDTO<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;
}
