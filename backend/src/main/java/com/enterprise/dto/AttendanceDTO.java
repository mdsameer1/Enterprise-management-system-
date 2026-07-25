package com.enterprise.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Attendance DTO
 * Data Transfer Object for Attendance entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private String status;
    private String checkInTime;
    private String checkOutTime;
    private String remarks;
    private String workingHours;
    private boolean approved;
    private String approvedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
