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
@Table(name = "leaves")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(length = 50, nullable = false)
    private String leaveType; // ANNUAL, SICK, CASUAL, MATERNITY, PATERNITY

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "number_of_days")
    private Double numberOfDays;

    @Column(length = 1000)
    private String reason;

    @Column(length = 50)
    @Builder.Default
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, CANCELLED

    @Column(name = "approved_by_id")
    private Long approvedById;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(length = 500)
    private String remarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
