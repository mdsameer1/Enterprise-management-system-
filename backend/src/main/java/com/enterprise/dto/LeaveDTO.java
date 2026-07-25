package com.enterprise.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Leave DTO
 * Data Transfer Object for Leave entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfDays;
    private String leaveType;
    private String status;
    private String reason;
    private Long approverIdId;
    private String approverName;
    private String approvalRemark;
    private Integer leaveYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
