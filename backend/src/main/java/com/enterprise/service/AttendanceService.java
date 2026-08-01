package com.enterprise.service;

import com.enterprise.dto.AttendanceDTO;
import com.enterprise.entity.Attendance;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.AttendanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceDTO checkIn(Long employeeId) {
        log.info("Check-in for employee: {}", employeeId);

        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(employeeId, today)
                .orElse(Attendance.builder()
                        .employeeId(employeeId)
                        .attendanceDate(today)
                        .status("PRESENT")
                        .build());

        attendance.setCheckInTime(LocalTime.now());
        attendance = attendanceRepository.save(attendance);
        return mapToDTO(attendance);
    }

    public AttendanceDTO checkOut(Long employeeId) {
        log.info("Check-out for employee: {}", employeeId);

        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(employeeId, today)
                .orElseThrow(() -> new ResourceNotFoundException("No check-in found for today"));

        attendance.setCheckOutTime(LocalTime.now());
        if (attendance.getCheckInTime() != null) {
            long minutes = java.time.temporal.ChronoUnit.MINUTES
                    .between(attendance.getCheckInTime(), attendance.getCheckOutTime());
            attendance.setWorkingHours(minutes / 60.0);
        }
        attendance = attendanceRepository.save(attendance);
        return mapToDTO(attendance);
    }

    public Page<AttendanceDTO> getAttendanceByEmployee(Long employeeId, Pageable pageable) {
        log.info("Fetching attendance for employee: {}", employeeId);
        return attendanceRepository.findByEmployeeId(employeeId, pageable).map(this::mapToDTO);
    }

    public Page<AttendanceDTO> getAttendanceDateRange(Long employeeId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("Fetching attendance for employee {} between {} and {}", employeeId, startDate, endDate);
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(employeeId, startDate, endDate, pageable)
                .map(this::mapToDTO);
    }

    private AttendanceDTO mapToDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployeeId())
                .attendanceDate(attendance.getAttendanceDate())
                .checkInTime(attendance.getCheckInTime())
                .checkOutTime(attendance.getCheckOutTime())
                .status(attendance.getStatus())
                .workingHours(attendance.getWorkingHours())
                .remarks(attendance.getRemarks())
                .createdAt(attendance.getCreatedAt())
                .build();
    }
}
