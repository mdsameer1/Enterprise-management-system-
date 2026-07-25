package com.enterprise.service;

import com.enterprise.entity.AuditLog;
import com.enterprise.entity.User;
import com.enterprise.repository.AuditLogRepository;
import com.enterprise.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Audit Service
 * 
 * Handles audit logging for compliance and security
 */
@Slf4j
@Service
@Transactional
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Log user action
     */
    public void logAction(Long userId, String action, String entityType, Long entityId,
                         String oldValues, String newValues, String ipAddress,
                         String userAgent, String status, String remarks) {
        try {
            User user = userId != null ? userRepository.findById(userId).orElse(null) : null;

            AuditLog auditLog = AuditLog.builder()
                    .user(user)
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .oldValues(oldValues)
                    .newValues(newValues)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .status(status)
                    .remarks(remarks)
                    .build();

            auditLogRepository.save(auditLog);
            log.info("Audit log created for action: {} on entity: {} with ID: {}", action, entityType, entityId);
        } catch (Exception ex) {
            log.error("Error creating audit log", ex);
        }
    }

    /**
     * Log simple action
     */
    public void logSimpleAction(Long userId, String action, String entityType, Long entityId) {
        logAction(userId, action, entityType, entityId, null, null, null, null, "SUCCESS", null);
    }
}
