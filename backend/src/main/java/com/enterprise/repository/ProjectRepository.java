package com.enterprise.repository;

import com.enterprise.entity.Project;
import com.enterprise.entity.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project Repository
 * 
 * Data access operations for Project entity
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);
    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);
    Page<Project> findByManager_Id(Long managerId, Pageable pageable);
    
    @Query("SELECT p FROM Project p JOIN p.teamMembers tm WHERE tm.id = :employeeId")
    Page<Project> findByTeamMember(@Param("employeeId") Long employeeId, Pageable pageable);
}