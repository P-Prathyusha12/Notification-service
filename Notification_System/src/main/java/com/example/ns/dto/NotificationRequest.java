package com.example.ns.dto;

import com.example.ns.model.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequest {
    @NotNull(message = "Channel type is required")
    private ChannelType channelType;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    // Getters and Setters
    public ChannelType getChannelType() { return channelType; }
    public void setChannelType(ChannelType channelType) { this.channelType = channelType; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}