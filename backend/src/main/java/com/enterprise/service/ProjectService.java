package com.enterprise.service;

import com.enterprise.dto.ProjectDTO;
import com.enterprise.entity.Project;
import com.enterprise.exception.BadRequestException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectDTO createProject(ProjectDTO projectDTO) {
        log.info("Creating new project: {}", projectDTO.getProjectCode());

        if (projectRepository.findByProjectCode(projectDTO.getProjectCode()).isPresent()) {
            throw new BadRequestException("Project code already exists");
        }

        Project project = Project.builder()
                .projectCode(projectDTO.getProjectCode())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate())
                .projectManagerId(projectDTO.getProjectManagerId())
                .status("ACTIVE")
                .priority(projectDTO.getPriority())
                .budget(projectDTO.getBudget())
                .actualCost(0.0)
                .progressPercentage(0)
                .clientName(projectDTO.getClientName())
                .createdAt(LocalDateTime.now())
                .build();

        project = projectRepository.save(project);
        return mapToDTO(project);
    }

    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        log.info("Updating project: {}", id);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (projectDTO.getName() != null) project.setName(projectDTO.getName());
        if (projectDTO.getDescription() != null) project.setDescription(projectDTO.getDescription());
        if (projectDTO.getStatus() != null) project.setStatus(projectDTO.getStatus());
        if (projectDTO.getPriority() != null) project.setPriority(projectDTO.getPriority());
        if (projectDTO.getProgressPercentage() != null) project.setProgressPercentage(projectDTO.getProgressPercentage());
        if (projectDTO.getActualCost() != null) project.setActualCost(projectDTO.getActualCost());
        if (projectDTO.getEndDate() != null) project.setEndDate(projectDTO.getEndDate());

        project.setUpdatedAt(LocalDateTime.now());
        project = projectRepository.save(project);
        return mapToDTO(project);
    }

    public ProjectDTO getProjectById(Long id) {
        log.info("Fetching project: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return mapToDTO(project);
    }

    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        log.info("Fetching all projects");
        return projectRepository.findAll(pageable).map(this::mapToDTO);
    }

    public Page<ProjectDTO> getProjectsByManager(Long managerId, Pageable pageable) {
        log.info("Fetching projects by manager: {}", managerId);
        return projectRepository.findByProjectManagerId(managerId, pageable).map(this::mapToDTO);
    }

    public void deleteProject(Long id) {
        log.info("Deleting project: {}", id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        project.setStatus("CANCELLED");
        project.setUpdatedAt(LocalDateTime.now());
        projectRepository.save(project);
    }

    private ProjectDTO mapToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .projectCode(project.getProjectCode())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .projectManagerId(project.getProjectManagerId())
                .status(project.getStatus())
                .priority(project.getPriority())
                .budget(project.getBudget())
                .actualCost(project.getActualCost())
                .progressPercentage(project.getProgressPercentage())
                .clientName(project.getClientName())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
