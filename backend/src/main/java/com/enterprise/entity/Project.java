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
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String projectCode;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "project_manager_id")
    private Long projectManagerId;

    @Column(length = 50)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, ON_HOLD, COMPLETED, CANCELLED

    @Column(length = 50)
    private String priority; // HIGH, MEDIUM, LOW

    @Column(name = "budget")
    private Double budget;

    @Column(name = "actual_cost")
    private Double actualCost;

    @Column(name = "progress_percentage")
    @Builder.Default
    private Integer progressPercentage = 0;

    @Column(name = "client_name")
    private String clientName;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
