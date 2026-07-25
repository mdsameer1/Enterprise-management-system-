package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Department DTO
 * Data Transfer Object for Department entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Long managerId;
    private String managerName;
    private String location;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer employeeCount;
}
