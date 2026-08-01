package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String taskCode;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "assigned_to_id")
    private Long assignedToId;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(length = 50)
    @Builder.Default
    private String status = "TODO"; // TODO, IN_PROGRESS, IN_REVIEW, COMPLETED

    @Column(length = 50)
    @Builder.Default
    private String priority = "MEDIUM"; // HIGH, MEDIUM, LOW

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Column(name = "actual_hours")
    private Double actualHours;

    @Column(name = "progress_percentage")
    @Builder.Default
    private Integer progressPercentage = 0;

    @Column(name = "parent_task_id")
    private Long parentTaskId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
