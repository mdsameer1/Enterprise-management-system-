package com.enterprise.repository;

import com.enterprise.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    Page<Notification> findByUserId(Long userId, Pageable pageable);
    List<Notification> findByUserIdAndIsRead(Long userId, Boolean isRead);
    Page<Notification> findByUserIdAndIsRead(Long userId, Boolean isRead, Pageable pageable);
    Long countByUserIdAndIsRead(Long userId, Boolean isRead);
}
