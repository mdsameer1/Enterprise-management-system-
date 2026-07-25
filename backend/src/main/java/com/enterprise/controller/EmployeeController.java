package com.enterprise.controller;

import com.enterprise.dto.EmployeeDTO;
import com.enterprise.dto.PaginationDTO;
import com.enterprise.dto.ApiResponseDTO;
import com.enterprise.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Employee Controller
 * 
 * Handles employee management endpoints:
 * - CRUD operations
 * - Search and filtering
 * - Pagination
 */
@Slf4j
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Employee management operations")
@SecurityRequirement(name = "bearer-jwt")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get all employees with pagination
     * GET /api/employees?page=0&size=20&sort=id,desc
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    @Operation(summary = "Get all employees", description = "Retrieve paginated list of employees")
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
            PaginationDTO<EmployeeDTO> employees = employeeService.getAllEmployees(pageable);

            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Employees retrieved successfully")
                    .data(employees)
                    .build());
        } catch (Exception ex) {
            log.error("Error retrieving employees: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("FETCH_ERROR")
                            .build());
        }
    }

    /**
     * Get employee by ID
     * GET /api/employees/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'EMPLOYEE')")
    @Operation(summary = "Get employee by ID", description = "Retrieve employee details by ID")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Employee retrieved successfully")
                    .data(employee)
                    .build());
        } catch (Exception ex) {
            log.error("Error retrieving employee: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("NOT_FOUND")
                            .build());
        }
    }

    /**
     * Create new employee
     * POST /api/employees
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @Operation(summary = "Create employee", description = "Create a new employee record")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDTO.builder()
                            .success(true)
                            .message("Employee created successfully")
                            .data(createdEmployee)
                            .build());
        } catch (Exception ex) {
            log.error("Error creating employee: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("CREATE_ERROR")
                            .build());
        }
    }

    /**
     * Update employee
     * PUT /api/employees/{id}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @Operation(summary = "Update employee", description = "Update employee information")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Employee updated successfully")
                    .data(updatedEmployee)
                    .build());
        } catch (Exception ex) {
            log.error("Error updating employee: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("UPDATE_ERROR")
                            .build());
        }
    }

    /**
     * Delete employee
     * DELETE /api/employees/{id}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete employee", description = "Delete an employee record")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok(ApiResponseDTO.builder()
                    .success(true)
                    .message("Employee deleted successfully")
                    .build());
        } catch (Exception ex) {
            log.error("Error deleting employee: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDTO.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .errorCode("DELETE_ERROR")
                            .build());
        }
    }
}
