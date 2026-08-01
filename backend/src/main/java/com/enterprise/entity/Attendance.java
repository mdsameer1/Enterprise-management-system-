package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    @Column(length = 50)
    @Builder.Default
    private String status = "PRESENT"; // PRESENT, ABSENT, HALF_DAY, ON_LEAVE, HOLIDAY

    @Column(name = "working_hours")
    private Double workingHours;

    @Column(length = 500)
    private String remarks;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
