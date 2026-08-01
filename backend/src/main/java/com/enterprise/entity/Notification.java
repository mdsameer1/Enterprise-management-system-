package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000)
    private String message;

    @Column(length = 50)
    @Builder.Default
    private String type = "INFO"; // INFO, WARNING, ERROR, SUCCESS

    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "reference_type")
    private String referenceType; // LEAVE, TASK, PROJECT, etc.

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "action_url")
    private String actionUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;
}
