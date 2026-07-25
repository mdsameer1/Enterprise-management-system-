package com.enterprise.controller;

import com.enterprise.dto.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Health Check Controller
 * 
 * Provides system health and status endpoints
 */
@Slf4j
@RestController
@RequestMapping("/api/health")
@Tag(name = "Health", description = "System health and status checks")
public class HealthController {

    /**
     * Health check endpoint
     * GET /api/health
     */
    @GetMapping
    @Operation(summary = "Health check", description = "Check if the API is running")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("status", "UP");
        healthData.put("timestamp", System.currentTimeMillis());
        healthData.put("message", "Enterprise Management System is running");

        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("System is healthy")
                .data(healthData)
                .build());
    }

    /**
     * Application info endpoint
     * GET /api/health/info
     */
    @GetMapping("/info")
    @Operation(summary = "Application info", description = "Get application information")
    public ResponseEntity<?> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "Enterprise Management System");
        info.put("version", "1.0.0");
        info.put("description", "Production-grade Employee & Project Management System");
        info.put("environment", System.getProperty("spring.profiles.active", "development"));

        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Application info retrieved")
                .data(info)
                .build());
    }
}
