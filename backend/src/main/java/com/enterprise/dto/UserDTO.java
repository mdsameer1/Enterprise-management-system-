package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * User DTO
 * Data Transfer Object for User entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean enabled;
    private boolean locked;
    private String profilePictureUrl;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}
