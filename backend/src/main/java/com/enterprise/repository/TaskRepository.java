package com.enterprise.repository;

import com.enterprise.entity.Task;
import com.enterprise.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Task Repository
 * 
 * Data access operations for Task entity
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskCode(String taskCode);
    Page<Task> findByProject_Id(Long projectId, Pageable pageable);
    Page<Task> findByAssignedTo_Id(Long employeeId, Pageable pageable);
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);
    List<Task> findByParentTask_Id(Long parentTaskId);
}