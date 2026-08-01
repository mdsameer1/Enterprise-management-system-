package com.enterprise.service;

import com.enterprise.dto.DepartmentDTO;
import com.enterprise.entity.Department;
import com.enterprise.exception.BadRequestException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        log.info("Creating new department: {}", departmentDTO.getName());

        if (departmentRepository.findByName(departmentDTO.getName()).isPresent()) {
            throw new BadRequestException("Department name already exists");
        }

        Department department = Department.builder()
                .name(departmentDTO.getName())
                .description(departmentDTO.getDescription())
                .departmentHeadId(departmentDTO.getDepartmentHeadId())
                .budget(departmentDTO.getBudget())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        department = departmentRepository.save(department);
        return mapToDTO(department);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        log.info("Updating department: {}", id);

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        if (departmentDTO.getName() != null) {
            department.setName(departmentDTO.getName());
        }
        if (departmentDTO.getDescription() != null) {
            department.setDescription(departmentDTO.getDescription());
        }
        if (departmentDTO.getDepartmentHeadId() != null) {
            department.setDepartmentHeadId(departmentDTO.getDepartmentHeadId());
        }
        if (departmentDTO.getBudget() != null) {
            department.setBudget(departmentDTO.getBudget());
        }
        if (departmentDTO.getIsActive() != null) {
            department.setIsActive(departmentDTO.getIsActive());
        }

        department.setUpdatedAt(LocalDateTime.now());
        department = departmentRepository.save(department);
        return mapToDTO(department);
    }

    public DepartmentDTO getDepartmentById(Long id) {
        log.info("Fetching department: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return mapToDTO(department);
    }

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        log.info("Fetching all departments");
        return departmentRepository.findAll(pageable).map(this::mapToDTO);
    }

    public List<DepartmentDTO> getActiveDepartments() {
        log.info("Fetching active departments");
        return departmentRepository.findByIsActive(true).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteDepartment(Long id) {
        log.info("Deleting department: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        department.setIsActive(false);
        department.setUpdatedAt(LocalDateTime.now());
        departmentRepository.save(department);
    }

    private DepartmentDTO mapToDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .departmentHeadId(department.getDepartmentHeadId())
                .budget(department.getBudget())
                .isActive(department.getIsActive())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}
