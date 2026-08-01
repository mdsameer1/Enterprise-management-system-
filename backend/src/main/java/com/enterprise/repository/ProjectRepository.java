package com.enterprise.repository;

import com.enterprise.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);
    List<Project> findByStatus(String status);
    Page<Project> findByStatus(String status, Pageable pageable);
    List<Project> findByProjectManagerId(Long projectManagerId);
    Page<Project> findByProjectManagerId(Long projectManagerId, Pageable pageable);
}
