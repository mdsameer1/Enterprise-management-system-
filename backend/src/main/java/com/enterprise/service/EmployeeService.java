package com.enterprise.service;

import com.enterprise.dto.EmployeeDTO;
import com.enterprise.entity.Employee;
import com.enterprise.entity.User;
import com.enterprise.exception.BadRequestException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.EmployeeRepository;
import com.enterprise.repository.UserRepository;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository employeeRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating new employee: {}", employeeDTO.getEmployeeCode());

        if (employeeRepository.findByEmployeeCode(employeeDTO.getEmployeeCode()).isPresent()) {
            throw new BadRequestException("Employee code already exists");
        }

        User user = userRepository.findById(employeeDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Employee employee = Employee.builder()
                .user(user)
                .employeeCode(employeeDTO.getEmployeeCode())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .gender(employeeDTO.getGender())
                .phoneNumber(employeeDTO.getPhoneNumber())
                .address(employeeDTO.getAddress())
                .city(employeeDTO.getCity())
                .state(employeeDTO.getState())
                .country(employeeDTO.getCountry())
                .postalCode(employeeDTO.getPostalCode())
                .designation(employeeDTO.getDesignation())
                .joiningDate(employeeDTO.getJoiningDate())
                .reportingManagerId(employeeDTO.getReportingManagerId())
                .salary(employeeDTO.getSalary())
                .employmentType(employeeDTO.getEmploymentType())
                .status("ACTIVE")
                .isManager(employeeDTO.getIsManager() != null ? employeeDTO.getIsManager() : false)
                .annualLeaveBalance(20)
                .createdAt(LocalDateTime.now())
                .build();

        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (employeeDTO.getPhoneNumber() != null) {
            employee.setPhoneNumber(employeeDTO.getPhoneNumber());
        }
        if (employeeDTO.getAddress() != null) {
            employee.setAddress(employeeDTO.getAddress());
        }
        if (employeeDTO.getCity() != null) {
            employee.setCity(employeeDTO.getCity());
        }
        if (employeeDTO.getDesignation() != null) {
            employee.setDesignation(employeeDTO.getDesignation());
        }
        if (employeeDTO.getSalary() != null) {
            employee.setSalary(employeeDTO.getSalary());
        }
        if (employeeDTO.getStatus() != null) {
            employee.setStatus(employeeDTO.getStatus());
        }
        if (employeeDTO.getReportingManagerId() != null) {
            employee.setReportingManagerId(employeeDTO.getReportingManagerId());
        }

        employee.setUpdatedAt(LocalDateTime.now());
        employee = employeeRepository.save(employee);
        return mapToDTO(employee);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return mapToDTO(employee);
    }

    public EmployeeDTO getEmployeeByCode(String code) {
        log.info("Fetching employee by code: {}", code);
        Employee employee = employeeRepository.findByEmployeeCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return mapToDTO(employee);
    }

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        log.info("Fetching all employees");
        return employeeRepository.findAll(pageable).map(this::mapToDTO);
    }

    public Page<EmployeeDTO> getEmployeesByDepartment(Long departmentId, Pageable pageable) {
        log.info("Fetching employees by department: {}", departmentId);
        return employeeRepository.findByDepartmentId(departmentId, pageable).map(this::mapToDTO);
    }

    public Page<EmployeeDTO> getEmployeesByStatus(String status, Pageable pageable) {
        log.info("Fetching employees by status: {}", status);
        return employeeRepository.findByStatus(status, pageable).map(this::mapToDTO);
    }

    public void deleteEmployee(Long id) {
        log.info("Deleting employee: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        employee.setStatus("INACTIVE");
        employee.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .userId(employee.getUser().getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getUser().getFirstName())
                .lastName(employee.getUser().getLastName())
                .email(employee.getUser().getEmail())
                .dateOfBirth(employee.getDateOfBirth())
                .gender(employee.getGender())
                .phoneNumber(employee.getPhoneNumber())
                .address(employee.getAddress())
                .city(employee.getCity())
                .state(employee.getState())
                .country(employee.getCountry())
                .postalCode(employee.getPostalCode())
                .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .departmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                .designation(employee.getDesignation())
                .joiningDate(employee.getJoiningDate())
                .reportingManagerId(employee.getReportingManagerId())
                .salary(employee.getSalary())
                .employmentType(employee.getEmploymentType())
                .status(employee.getStatus())
                .isManager(employee.getIsManager())
                .annualLeaveBalance(employee.getAnnualLeaveBalance())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
