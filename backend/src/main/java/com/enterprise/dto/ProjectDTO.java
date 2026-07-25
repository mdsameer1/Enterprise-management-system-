package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Project DTO
 * Data Transfer Object for Project entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long id;
    private String projectCode;
    private String name;
    private String description;
    private Long managerId;
    private String managerName;
    private String status;
    private String startDate;
    private String endDate;
    private String dueDate;
    private Double budget;
    private Double progress;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer taskCount;
    private Integer teamMemberCount;
}
