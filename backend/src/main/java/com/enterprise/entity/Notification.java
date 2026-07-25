package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Notification Entity
 * 
 * Stores in-app notifications for users
 */
@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_read_status", columnList = "is_read")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType; // TASK_ASSIGNED, LEAVE_APPROVED, etc

    @Column(length = 50)
    private String relatedEntity; // task, leave, employee, etc

    @Column
    private Long relatedEntityId;

    @Column(nullable = false)
    private boolean isRead = false;

    @Column(length = 50)
    private String priority; // LOW, MEDIUM, HIGH

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

enum NotificationType {
    TASK_ASSIGNED,
    TASK_COMPLETED,
    LEAVE_APPROVED,
    LEAVE_REJECTED,
    PROJECT_UPDATED,
    ATTENDANCE_MARKED,
    SYSTEM_ALERT
}