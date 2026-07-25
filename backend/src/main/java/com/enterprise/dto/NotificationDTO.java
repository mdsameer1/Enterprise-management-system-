package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Notification DTO
 * Data Transfer Object for Notification entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String notificationType;
    private String relatedEntity;
    private Long relatedEntityId;
    private boolean isRead;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
