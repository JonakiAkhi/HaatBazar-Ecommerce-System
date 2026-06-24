package com.Haat_Bazar.notification_service.repository;

import com.Haat_Bazar.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository — the layer that talks to the database.
 *
 * By extending JpaRepository we get save(), findById(), findAll(), delete()
 * etc. for FREE — no SQL needed.
 *
 * We can also define custom queries just by naming the method correctly:
 *   findByRecipientOrderBySentAtDesc  →  SELECT * FROM notifications
 *                                         WHERE recipient = ?
 *                                         ORDER BY sent_at DESC
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientOrderBySentAtDesc(String recipient);
}
