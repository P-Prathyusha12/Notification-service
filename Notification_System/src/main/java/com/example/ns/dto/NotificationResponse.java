package com.example.ns.dto;

import java.time.LocalDateTime;

import com.example.ns.model.ChannelType;
import com.example.ns.model.NotificationStatus;

public class NotificationResponse {
    private Long id;
    private String message;
    private ChannelType channelType;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private String errorMessage;
    
    // Constructors
    public NotificationResponse(Long id, String message, ChannelType channelType, 
                                NotificationStatus status, LocalDateTime sentAt, String errorMessage) {
        this.id = id;
        this.message = message;
        this.channelType = channelType;
        this.status = status;
        this.sentAt = sentAt;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }
    
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}