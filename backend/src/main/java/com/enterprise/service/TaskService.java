package com.enterprise.service;

import com.enterprise.dto.TaskDTO;
import com.enterprise.entity.Task;
import com.enterprise.exception.BadRequestException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Creating new task: {}", taskDTO.getTaskCode());

        if (taskRepository.findByTaskCode(taskDTO.getTaskCode()).isPresent()) {
            throw new BadRequestException("Task code already exists");
        }

        Task task = Task.builder()
                .taskCode(taskDTO.getTaskCode())
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .startDate(taskDTO.getStartDate())
                .dueDate(taskDTO.getDueDate())
                .assignedToId(taskDTO.getAssignedToId())
                .createdById(taskDTO.getCreatedById())
                .status("TODO")
                .priority(taskDTO.getPriority() != null ? taskDTO.getPriority() : "MEDIUM")
                .estimatedHours(taskDTO.getEstimatedHours())
                .actualHours(0.0)
                .progressPercentage(0)
                .parentTaskId(taskDTO.getParentTaskId())
                .createdAt(LocalDateTime.now())
                .build();

        task = taskRepository.save(task);
        return mapToDTO(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Updating task: {}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (taskDTO.getTitle() != null) task.setTitle(taskDTO.getTitle());
        if (taskDTO.getDescription() != null) task.setDescription(taskDTO.getDescription());
        if (taskDTO.getStatus() != null) task.setStatus(taskDTO.getStatus());
        if (taskDTO.getPriority() != null) task.setPriority(taskDTO.getPriority());
        if (taskDTO.getProgressPercentage() != null) task.setProgressPercentage(taskDTO.getProgressPercentage());
        if (taskDTO.getActualHours() != null) task.setActualHours(taskDTO.getActualHours());
        if (taskDTO.getAssignedToId() != null) task.setAssignedToId(taskDTO.getAssignedToId());
        if (taskDTO.getDueDate() != null) task.setDueDate(taskDTO.getDueDate());

        task.setUpdatedAt(LocalDateTime.now());
        task = taskRepository.save(task);
        return mapToDTO(task);
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Fetching task: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return mapToDTO(task);
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        log.info("Fetching all tasks");
        return taskRepository.findAll(pageable).map(this::mapToDTO);
    }

    public Page<TaskDTO> getTasksByProject(Long projectId, Pageable pageable) {
        log.info("Fetching tasks by project: {}", projectId);
        return taskRepository.findByProjectId(projectId, pageable).map(this::mapToDTO);
    }

    public Page<TaskDTO> getTasksByAssignee(Long assigneeId, Pageable pageable) {
        log.info("Fetching tasks by assignee: {}", assigneeId);
        return taskRepository.findByAssignedToId(assigneeId, pageable).map(this::mapToDTO);
    }

    public void deleteTask(Long id) {
        log.info("Deleting task: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus("COMPLETED");
        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    private TaskDTO mapToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .taskCode(task.getTaskCode())
                .title(task.getTitle())
                .description(task.getDescription())
                .projectId(task.getProject().getId())
                .assignedToId(task.getAssignedToId())
                .createdById(task.getCreatedById())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .estimatedHours(task.getEstimatedHours())
                .actualHours(task.getActualHours())
                .progressPercentage(task.getProgressPercentage())
                .parentTaskId(task.getParentTaskId())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
