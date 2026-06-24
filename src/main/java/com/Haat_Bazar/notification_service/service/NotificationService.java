package com.Haat_Bazar.notification_service.service;

import com.Haat_Bazar.notification_service.dto.NotificationRequest;
import com.Haat_Bazar.notification_service.entity.Notification;
import com.Haat_Bazar.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    public Notification sendNotification(NotificationRequest request) {
        // 1. Create the entity and set status to PENDING
        Notification notification = new Notification();
        notification.setRecipient(request.getEmail());
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setStatus("PENDING");
        notification.setSentAt(LocalDateTime.now());
        
        notification = notificationRepository.save(notification);

        // 2. "Send" the email asynchronously
        try {
            // This is an @Async method, so it returns immediately
            emailService.sendEmail(request.getEmail(), request.getSubject(), request.getMessage());
            
            // For simplicity, we assume it succeeds here. 
            // In a production system, the async method would update the status upon actual completion/failure.
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        }

        // 3. Save the final status
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(String email) {
        return notificationRepository.findByRecipientOrderBySentAtDesc(email);
    }
}
