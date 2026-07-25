package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Task Entity
 * 
 * Represents tasks within projects
 */
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_code", columnList = "taskCode", unique = true),
        @Index(name = "idx_project_id", columnList = "project_id"),
        @Index(name = "idx_assigned_to", columnList = "assigned_to_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String taskCode;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private Employee assignedTo;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String dueDate;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private TaskStatus status; // TODO, IN_PROGRESS, IN_REVIEW, COMPLETED, BLOCKED

    @Column(length = 50)
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(precision = 5, scale = 2)
    private Double progress = 0.0;

    @Column(length = 500)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

enum TaskStatus {
    TODO,
    IN_PROGRESS,
    IN_REVIEW,
    COMPLETED,
    BLOCKED
}