package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Task DTO
 * Data Transfer Object for Task entity
 */
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
    private String startDate;
    private String dueDate;
    private String status;
    private String priority;
    private Double progress;
    private String comments;
    private Long parentTaskId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
