package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Employee Entity
 * 
 * Stores employee information linked to User account
 */
@Entity
@Table(name = "employees", indexes = {
        @Index(name = "idx_employee_id", columnList = "employeeId", unique = true),
        @Index(name = "idx_department_id", columnList = "department_id"),
        @Index(name = "idx_manager_id", columnList = "manager_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String employeeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 200)
    private String designation;

    @Column(length = 100)
    private String department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department departmentEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 10)
    private String zipCode;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String dateOfBirth;

    @Column(length = 20)
    private String gender;

    @Column(length = 50)
    private String bloodGroup;

    @Column(length = 100)
    private String panNumber;

    @Column(length = 100)
    private String aadharNumber;

    @Column(nullable = false)
    private String joinDate;

    @Column(length = 50)
    private String employmentType; // Permanent, Contract, Intern

    @Column(precision = 10, scale = 2)
    private Double salary;

    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}