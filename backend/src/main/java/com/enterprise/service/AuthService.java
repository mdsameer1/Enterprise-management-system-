package com.enterprise.service;

import com.enterprise.dto.*;
import com.enterprise.entity.User;
import com.enterprise.entity.UserRole;
import com.enterprise.exception.BusinessException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.UserRepository;
import com.enterprise.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Authentication Service
 * 
 * Handles user login, registration, token management
 */
@Slf4j
@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Login user with email and password
     */
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginRequest.getEmail()));

            if (user.isLocked()) {
                throw new BusinessException("Account is locked. Please contact administrator.");
            }

            if (!user.isEnabled()) {
                throw new BusinessException("Account is not verified. Please verify your email.");
            }

            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);

            log.info("User logged in successfully: {}", loginRequest.getEmail());

            return LoginResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(3600L)
                    .user(mapToUserDTO(user))
                    .build();
        } catch (BadCredentialsException ex) {
            log.error("Invalid credentials for user: {}", loginRequest.getEmail());
            throw new BusinessException("Invalid email or password");
        }
    }

    /**
     * Register new user
     */
    public UserDTO register(RegisterRequestDTO registerRequest) {
        log.info("Attempting registration for user: {}", registerRequest.getEmail());

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BusinessException("Passwords do not match");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BusinessException("Username already taken");
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.EMPLOYEE)
                .enabled(false)
                .locked(false)
                .lastLoginAt(LocalDateTime.now())
                .createdBy(registerRequest.getEmail())
                .updatedBy(registerRequest.getEmail())
                .build();

        user = userRepository.save(user);

        log.info("User registered successfully: {}", registerRequest.getEmail());

        return mapToUserDTO(user);
    }

    /**
     * Refresh access token
     */
    public LoginResponseDTO refreshAccessToken(String refreshToken) {
        log.info("Refreshing access token");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        String email = jwtTokenProvider.getUsernameFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String newAccessToken = jwtTokenProvider.generateAccessTokenFromUsername(email);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(email);

        return LoginResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(3600L)
                .user(mapToUserDTO(user))
                .build();
    }

    /**
     * Map User entity to UserDTO
     */
    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .locked(user.isLocked())
                .profilePictureUrl(user.getProfilePictureUrl())
                .phoneNumber(user.getPhoneNumber())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
