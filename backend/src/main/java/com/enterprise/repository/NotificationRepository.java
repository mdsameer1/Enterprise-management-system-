package com.enterprise.repository;

import com.enterprise.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Notification Repository
 * 
 * Data access operations for Notification entity
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser_Id(Long userId, Pageable pageable);
    Page<Notification> findByUser_IdAndIsRead(Long userId, boolean isRead, Pageable pageable);
    Long countByUser_IdAndIsReadFalse(Long userId);
}