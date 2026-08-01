package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(length = 20)
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "designation")
    private String designation;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "reporting_manager_id")
    private Long reportingManagerId;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "employment_type", length = 50)
    private String employmentType; // FULL_TIME, PART_TIME, CONTRACT

    @Column(name = "status", nullable = false)
    @Builder.Default
    private String status = "ACTIVE"; // ACTIVE, INACTIVE, ON_LEAVE

    @Column(name = "is_manager")
    @Builder.Default
    private Boolean isManager = false;

    @Column(name = "annual_leave_balance")
    @Builder.Default
    private Integer annualLeaveBalance = 20;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
