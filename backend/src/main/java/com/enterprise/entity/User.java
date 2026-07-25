package com.enterprise.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User Entity
 * 
 * Represents a system user with authentication and role information
 * Roles: ADMIN, HR, MANAGER, EMPLOYEE
 */
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true),
        @Index(name = "idx_username", columnList = "username", unique = true),
        @Index(name = "idx_role", columnList = "role")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean locked = false;

    @Column(length = 500)
    private String profilePictureUrl;

    @Column(length = 500)
    private String phoneNumber;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, updatable = false)
    private String createdBy;

    @Column(nullable = false)
    private String updatedBy;

    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

    private LocalDateTime lastPasswordChangedAt;

    @Version
    private Long version;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Employee employee;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefreshToken> refreshTokens = new HashSet<>();
}

enum UserRole {
    ADMIN,
    HR,
    MANAGER,
    EMPLOYEE
}