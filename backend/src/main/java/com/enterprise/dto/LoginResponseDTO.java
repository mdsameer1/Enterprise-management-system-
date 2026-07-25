package com.enterprise.dto;

import lombok.*;

/**
 * Login Response DTO
 * Contains JWT tokens after successful authentication
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserDTO user;
}
