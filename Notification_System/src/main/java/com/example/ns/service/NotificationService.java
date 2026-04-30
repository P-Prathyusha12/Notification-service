package com.example.ns.service;

import com.example.ns.dto.NotificationResponse;
import com.example.ns.exception.UserNotFoundException;
import com.example.ns.model.*;
import com.example.ns.repository.NotificationHistoryRepository;
import com.example.ns.repository.PreferenceRepository;
import com.example.ns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Autowired
    private NotificationHistoryRepository historyRepository;
    
    @Transactional
    public NotificationResponse sendNotification(Long userId, ChannelType channelType, String message) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        NotificationPreference preference = preferenceRepository.findByUser(user)
                .orElseGet(() -> {
                    NotificationPreference newPref = new NotificationPreference(user);
                    return preferenceRepository.save(newPref);
                });
        
        boolean isEnabled = false;
        switch (channelType) {
            case EMAIL:
                isEnabled = preference.isEmailEnabled();
                break;
            case SMS:
                isEnabled = preference.isSmsEnabled();
                break;
            case PUSH:
                isEnabled = preference.isPushEnabled();
                break;
        }
        
        NotificationHistory notification;
        
        if (!isEnabled) {
            notification = new NotificationHistory(user, message, channelType, NotificationStatus.FAILED);
            notification.setErrorMessage(channelType + " channel is disabled for this user");
        } else {
            // Simulate 95% delivery success rate
            boolean delivered = Math.random() < 0.95;
            notification = new NotificationHistory(user, message, channelType, 
                    delivered ? NotificationStatus.SENT : NotificationStatus.FAILED);
            if (!delivered) {
                notification.setErrorMessage("Delivery simulation failed");
            }
        }
        
        notification = historyRepository.save(notification);
        
        return new NotificationResponse(notification.getId(), notification.getMessage(),
                notification.getChannelType(), notification.getStatus(),
                notification.getSentAt(), notification.getErrorMessage());
    }
    
    public List<NotificationResponse> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        return historyRepository.findByUser(user).stream()
                .map(n -> new NotificationResponse(n.getId(), n.getMessage(),
                        n.getChannelType(), n.getStatus(), n.getSentAt(), n.getErrorMessage()))
                .collect(Collectors.toList());
    }
    
    public List<NotificationResponse> getUserNotificationsByStatus(Long userId, NotificationStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        return historyRepository.findByUserAndStatus(user, status).stream()
                .map(n -> new NotificationResponse(n.getId(), n.getMessage(),
                        n.getChannelType(), n.getStatus(), n.getSentAt(), n.getErrorMessage()))
                .collect(Collectors.toList());
    }
    
    public List<NotificationResponse> getUserNotificationsByType(Long userId, ChannelType channelType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        return historyRepository.findByUserAndChannelType(user, channelType).stream()
                .map(n -> new NotificationResponse(n.getId(), n.getMessage(),
                        n.getChannelType(), n.getStatus(), n.getSentAt(), n.getErrorMessage()))
                .collect(Collectors.toList());
    }
}