package com.enterprise.controller;

import com.enterprise.dto.*;
import com.enterprise.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication Controller
 * 
 * Handles user authentication endpoints:
 * - Login
 * - Registration
 * - Token refresh
 * - Password reset
 * - Email verification
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication and account management")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login endpoint
     * POST /api/auth/login
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user with email and password")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());
        try {
            LoginResponseDTO response = authService.login(loginRequest);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Login successful")
                    .data(response)
                    .build());
        } catch (Exception ex) {
            log.error("Login failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("LOGIN_FAILED")
                            .build());
        }
    }

    /**
     * Registration endpoint
     * POST /api/auth/register
     */
    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        log.info("Registration attempt for email: {}", registerRequest.getEmail());
        try {
            UserDTO user = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .success(true)
                            .message("Registration successful. Please verify your email.")
                            .data(user)
                            .build());
        } catch (Exception ex) {
            log.error("Registration failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("REGISTRATION_FAILED")
                            .build());
        }
    }

    /**
     * Refresh access token endpoint
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Get a new access token using refresh token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        log.info("Token refresh attempt");
        try {
            String token = refreshToken.replace("Bearer ", "");
            LoginResponseDTO response = authService.refreshAccessToken(token);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Token refreshed successfully")
                    .data(response)
                    .build());
        } catch (Exception ex) {
            log.error("Token refresh failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("TOKEN_REFRESH_FAILED")
                            .build());
        }
    }

    /**
     * Verify email endpoint
     * POST /api/auth/verify-email
     */
    @PostMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verify user email with verification token")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        log.info("Email verification attempt");
        // TODO: Implement email verification logic
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Email verified successfully")
                .build());
    }

    /**
     * Forgot password endpoint
     * POST /api/auth/forgot-password
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Request password reset")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        log.info("Password reset request for email: {}", email);
        // TODO: Implement forgot password logic
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Password reset email sent")
                .build());
    }

    /**
     * Reset password endpoint
     * POST /api/auth/reset-password
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password with reset token")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        log.info("Password reset attempt");
        // TODO: Implement password reset logic
        return ResponseEntity.ok(ApiResponseDTO.builder()
                .success(true)
                .message("Password reset successfully")
                .build());
    }
}
