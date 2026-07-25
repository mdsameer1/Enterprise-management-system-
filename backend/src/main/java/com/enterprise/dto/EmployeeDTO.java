package com.enterprise.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Employee DTO
 * Data Transfer Object for Employee entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String employeeId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String designation;
    private String department;
    private Long departmentId;
    private Long managerId;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String panNumber;
    private String aadharNumber;
    private String joinDate;
    private String employmentType;
    private Double salary;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
