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
public class ProjectDTO {
    private Long id;
    private String projectCode;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long projectManagerId;
    private String projectManagerName;
    private String status;
    private String priority;
    private Double budget;
    private Double actualCost;
    private Integer progressPercentage;
    private String clientName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
