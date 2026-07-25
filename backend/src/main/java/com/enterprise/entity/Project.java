package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Project Entity
 * 
 * Represents projects managed in the system
 */
@Entity
@Table(name = "projects", indexes = {
        @Index(name = "idx_project_code", columnList = "projectCode", unique = true),
        @Index(name = "idx_project_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String projectCode;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Employee manager;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status; // PLANNING, ACTIVE, ON_HOLD, COMPLETED, CANCELLED

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String dueDate;

    @Column(precision = 5, scale = 2)
    private Double budget;

    @Column(precision = 5, scale = 2)
    private Double progress = 0.0;

    @Column(length = 50)
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Task> tasks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> teamMembers = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

enum ProjectStatus {
    PLANNING,
    ACTIVE,
    ON_HOLD,
    COMPLETED,
    CANCELLED
}