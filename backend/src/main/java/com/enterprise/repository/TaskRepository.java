package com.enterprise.repository;

import com.enterprise.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskCode(String taskCode);
    List<Task> findByProjectId(Long projectId);
    Page<Task> findByProjectId(Long projectId, Pageable pageable);
    List<Task> findByAssignedToId(Long assignedToId);
    Page<Task> findByAssignedToId(Long assignedToId, Pageable pageable);
    List<Task> findByStatus(String status);
    Page<Task> findByStatus(String status, Pageable pageable);
}
