package com.enterprise.repository;

import com.enterprise.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate date);
    List<Attendance> findByEmployeeId(Long employeeId);
    Page<Attendance> findByEmployeeId(Long employeeId, Pageable pageable);
    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);
    Page<Attendance> findByEmployeeIdAndAttendanceDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
