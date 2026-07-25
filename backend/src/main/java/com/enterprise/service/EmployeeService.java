package com.enterprise.service;

import com.enterprise.dto.EmployeeDTO;
import com.enterprise.dto.PaginationDTO;
import com.enterprise.entity.Employee;
import com.enterprise.entity.User;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.EmployeeRepository;
import com.enterprise.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Employee Service
 * 
 * Business logic for employee management
 */
@Slf4j
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all employees with pagination
     */
    public PaginationDTO<EmployeeDTO> getAllEmployees(Pageable pageable) {
        log.info("Fetching all employees");
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return mapToPageDTO(employees);
    }

    /**
     * Get employee by ID
     */
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        return mapToDTO(employee);
    }

    /**
     * Get employee by employee ID
     */
    public EmployeeDTO getEmployeeByEmployeeId(String employeeId) {
        log.info("Fetching employee with employee ID: {}", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeId", employeeId));
        return mapToDTO(employee);
    }

    /**
     * Create new employee
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating new employee: {}", employeeDTO.getFirstName());

        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", employeeDTO.getUserId()));

        Employee employee = Employee.builder()
                .employeeId(generateEmployeeId())
                .user(user)
                .firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .designation(employeeDTO.getDesignation())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .address(employeeDTO.getAddress())
                .city(employeeDTO.getCity())
                .state(employeeDTO.getState())
                .zipCode(employeeDTO.getZipCode())
                .country(employeeDTO.getCountry())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .gender(employeeDTO.getGender())
                .bloodGroup(employeeDTO.getBloodGroup())
                .panNumber(employeeDTO.getPanNumber())
                .aadharNumber(employeeDTO.getAadharNumber())
                .joinDate(employeeDTO.getJoinDate())
                .employmentType(employeeDTO.getEmploymentType())
                .salary(employeeDTO.getSalary())
                .active(true)
                .build();

        employee = employeeRepository.save(employee);
        log.info("Employee created successfully with ID: {}", employee.getId());
        return mapToDTO(employee);
    }

    /**
     * Update employee
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));

        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDesignation(employeeDTO.getDesignation());
        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        employee.setAddress(employeeDTO.getAddress());
        employee.setCity(employeeDTO.getCity());
        employee.setState(employeeDTO.getState());
        employee.setZipCode(employeeDTO.getZipCode());
        employee.setCountry(employeeDTO.getCountry());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setGender(employeeDTO.getGender());
        employee.setBloodGroup(employeeDTO.getBloodGroup());
        employee.setSalary(employeeDTO.getSalary());

        employee = employeeRepository.save(employee);
        log.info("Employee updated successfully with ID: {}", id);
        return mapToDTO(employee);
    }

    /**
     * Delete employee
     */
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
        employeeRepository.delete(employee);
        log.info("Employee deleted successfully with ID: {}", id);
    }

    /**
     * Generate unique employee ID
     */
    private String generateEmployeeId() {
        return "EMP" + System.currentTimeMillis();
    }

    /**
     * Map Employee entity to EmployeeDTO
     */
    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .employeeId(employee.getEmployeeId())
                .userId(employee.getUser().getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .designation(employee.getDesignation())
                .department(employee.getDepartment())
                .departmentId(employee.getDepartmentEntity() != null ? employee.getDepartmentEntity().getId() : null)
                .managerId(employee.getManager() != null ? employee.getManager().getId() : null)
                .phoneNumber(employee.getPhoneNumber())
                .address(employee.getAddress())
                .city(employee.getCity())
                .state(employee.getState())
                .zipCode(employee.getZipCode())
                .country(employee.getCountry())
                .dateOfBirth(employee.getDateOfBirth())
                .gender(employee.getGender())
                .bloodGroup(employee.getBloodGroup())
                .panNumber(employee.getPanNumber())
                .aadharNumber(employee.getAadharNumber())
                .joinDate(employee.getJoinDate())
                .employmentType(employee.getEmploymentType())
                .salary(employee.getSalary())
                .active(employee.isActive())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    /**
     * Map Page<Employee> to PaginationDTO
     */
    private PaginationDTO<EmployeeDTO> mapToPageDTO(Page<Employee> page) {
        return PaginationDTO.<EmployeeDTO>builder()
                .content(page.getContent().stream().map(this::mapToDTO).toList())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .build();
    }
}
