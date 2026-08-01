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
public class EmployeeDTO {
    private Long id;
    private Long userId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private Long departmentId;
    private String departmentName;
    private String designation;
    private LocalDate joiningDate;
    private Long reportingManagerId;
    private Double salary;
    private String employmentType;
    private String status;
    private Boolean isManager;
    private Integer annualLeaveBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
