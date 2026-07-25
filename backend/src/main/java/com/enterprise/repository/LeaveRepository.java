package com.enterprise.repository;

import com.enterprise.entity.Leave;
import com.enterprise.entity.LeaveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Leave Repository
 * 
 * Data access operations for Leave entity
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Page<Leave> findByEmployee_Id(Long employeeId, Pageable pageable);
    Page<Leave> findByStatus(LeaveStatus status, Pageable pageable);
    
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND l.leaveYear = :year")
    List<Leave> findByEmployeeAndYear(
            @Param("employeeId") Long employeeId,
            @Param("year") Integer year);
    
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND l.startDate <= :date AND l.endDate >= :date")
    List<Leave> findActiveLeaveOnDate(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date);
}