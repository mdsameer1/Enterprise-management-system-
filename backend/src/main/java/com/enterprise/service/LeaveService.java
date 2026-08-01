package com.enterprise.service;

import com.enterprise.dto.LeaveDTO;
import com.enterprise.entity.Leave;
import com.enterprise.exception.BadRequestException;
import com.enterprise.exception.ResourceNotFoundException;
import com.enterprise.repository.LeaveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@Transactional
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public LeaveDTO requestLeave(LeaveDTO leaveDTO) {
        log.info("Leave request from employee: {}", leaveDTO.getEmployeeId());

        if (leaveDTO.getStartDate().isAfter(leaveDTO.getEndDate())) {
            throw new BadRequestException("Start date must be before end date");
        }

        Double numberOfDays = (double) ChronoUnit.DAYS.between(leaveDTO.getStartDate(), leaveDTO.getEndDate()) + 1;

        Leave leave = Leave.builder()
                .employeeId(leaveDTO.getEmployeeId())
                .leaveType(leaveDTO.getLeaveType())
                .startDate(leaveDTO.getStartDate())
                .endDate(leaveDTO.getEndDate())
                .numberOfDays(numberOfDays)
                .reason(leaveDTO.getReason())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        leave = leaveRepository.save(leave);
        return mapToDTO(leave);
    }

    public LeaveDTO approveLeave(Long leaveId, Long approvedById) {
        log.info("Approving leave: {}", leaveId);

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));

        leave.setStatus("APPROVED");
        leave.setApprovedById(approvedById);
        leave.setApprovalDate(java.time.LocalDate.now());
        leave = leaveRepository.save(leave);
        return mapToDTO(leave);
    }

    public LeaveDTO rejectLeave(Long leaveId, String remarks) {
        log.info("Rejecting leave: {}", leaveId);

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Leave not found"));

        leave.setStatus("REJECTED");
        leave.setRemarks(remarks);
        leave = leaveRepository.save(leave);
        return mapToDTO(leave);
    }

    public Page<LeaveDTO> getLeavesByEmployee(Long employeeId, Pageable pageable) {
        log.info("Fetching leaves for employee: {}", employeeId);
        return leaveRepository.findByEmployeeId(employeeId, pageable).map(this::mapToDTO);
    }

    public Page<LeaveDTO> getPendingLeaves(Pageable pageable) {
        log.info("Fetching pending leaves");
        return leaveRepository.findByStatus("PENDING", pageable).map(this::mapToDTO);
    }

    private LeaveDTO mapToDTO(Leave leave) {
        return LeaveDTO.builder()
                .id(leave.getId())
                .employeeId(leave.getEmployeeId())
                .leaveType(leave.getLeaveType())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .numberOfDays(leave.getNumberOfDays())
                .reason(leave.getReason())
                .status(leave.getStatus())
                .approvedById(leave.getApprovedById())
                .approvalDate(leave.getApprovalDate())
                .remarks(leave.getRemarks())
                .createdAt(leave.getCreatedAt())
                .updatedAt(leave.getUpdatedAt())
                .build();
    }
}
