package com.Haat_Bazar.notification_service.controller;

import com.Haat_Bazar.notification_service.dto.NotificationRequest;
import com.Haat_Bazar.notification_service.entity.Notification;
import com.Haat_Bazar.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // POST /api/notifications/send
    @PostMapping("/send")
    public ResponseEntity<Notification> send(@Valid @RequestBody NotificationRequest request) {
        Notification response = notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/notifications?email=user@example.com
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam String email) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(email));
    }
}
