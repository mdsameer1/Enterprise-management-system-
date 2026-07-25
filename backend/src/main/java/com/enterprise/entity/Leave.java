package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Leave Entity
 * 
 * Manages leave requests and approvals
 */
@Entity
@Table(name = "leaves", indexes = {
        @Index(name = "idx_employee_leave", columnList = "employee_id, leave_year"),
        @Index(name = "idx_leave_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer numberOfDays;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType; // CASUAL, SICK, EARNED, MATERNITY, PATERNITY, UNPAID

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private LeaveStatus status; // PENDING, APPROVED, REJECTED

    @Column(columnDefinition = "TEXT")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approver;

    @Column(columnDefinition = "TEXT")
    private String approvalRemark;

    @Column(nullable = false)
    private Integer leaveYear;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

enum LeaveType {
    CASUAL,
    SICK,
    EARNED,
    MATERNITY,
    PATERNITY,
    UNPAID
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}