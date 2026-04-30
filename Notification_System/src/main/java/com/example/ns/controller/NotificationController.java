package com.example.ns.controller;

import com.example.ns.dto.NotificationRequest;
import com.example.ns.dto.NotificationResponse;
import com.example.ns.model.ChannelType;
import com.example.ns.model.NotificationStatus;
import com.example.ns.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/notifications")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> sendNotification(
            @PathVariable Long userId,
            @Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.sendNotification(
                userId, request.getChannelType(), request.getMessage());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationResponse> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponse>> getUserNotificationsByStatus(
            @PathVariable Long userId,
            @PathVariable NotificationStatus status) {
        List<NotificationResponse> notifications = notificationService.getUserNotificationsByStatus(userId, status);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/type/{channelType}")
    public ResponseEntity<List<NotificationResponse>> getUserNotificationsByType(
            @PathVariable Long userId,
            @PathVariable ChannelType channelType) {
        List<NotificationResponse> notifications = notificationService.getUserNotificationsByType(userId, channelType);
        return ResponseEntity.ok(notifications);
    }
}