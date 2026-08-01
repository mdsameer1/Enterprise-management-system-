package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(length = 100, nullable = false)
    private String action;

    @Column(length = 100, nullable = false)
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(length = 500)
    private String description;

    @Column(name = "old_value", length = 1000)
    private String oldValue;

    @Column(name = "new_value", length = 1000)
    private String newValue;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "status", length = 50)
    @Builder.Default
    private String status = "SUCCESS"; // SUCCESS, FAILURE

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
