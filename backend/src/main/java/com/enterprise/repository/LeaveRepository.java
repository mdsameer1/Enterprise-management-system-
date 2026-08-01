package com.enterprise.repository;

import com.enterprise.entity.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeId(Long employeeId);
    Page<Leave> findByEmployeeId(Long employeeId, Pageable pageable);
    List<Leave> findByStatus(String status);
    Page<Leave> findByStatus(String status, Pageable pageable);
    List<Leave> findByEmployeeIdAndStatus(Long employeeId, String status);
    List<Leave> findByEmployeeIdAndStartDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
}
