package com.enterprise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String taskCode;
    private String title;
    private String description;
    private Long projectId;
    private String projectName;
    private Long assignedToId;
    private String assignedToName;
    private Long createdById;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;
    private String priority;
    private Double estimatedHours;
    private Double actualHours;
    private Integer progressPercentage;
    private Long parentTaskId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
