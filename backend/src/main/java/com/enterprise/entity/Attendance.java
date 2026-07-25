package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Attendance Entity
 * 
 * Records daily attendance for employees
 */
@Entity
@Table(name = "attendance", indexes = {
        @Index(name = "idx_employee_date", columnList = "employee_id, attendance_date"),
        @Index(name = "idx_attendance_date", columnList = "attendance_date")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // PRESENT, ABSENT, HALF_DAY, LEAVE, WORK_FROM_HOME

    @Column(nullable = false, columnDefinition = "TIME")
    private String checkInTime;

    @Column(columnDefinition = "TIME")
    private String checkOutTime;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(length = 50)
    private String workingHours;

    @Column(nullable = false)
    private boolean approved = false;

    @Column(length = 200)
    private String approvedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}

enum AttendanceStatus {
    PRESENT,
    ABSENT,
    HALF_DAY,
    LEAVE,
    WORK_FROM_HOME
}