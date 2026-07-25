package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Department Entity
 * 
 * Represents organizational departments
 */
@Entity
@Table(name = "departments", indexes = {
        @Index(name = "idx_dept_name", columnList = "name", unique = true),
        @Index(name = "idx_dept_code", columnList = "code", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(length = 200)
    private String location;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "departmentEntity", cascade = CascadeType.ALL)
    private Set<Employee> employees = new HashSet<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}