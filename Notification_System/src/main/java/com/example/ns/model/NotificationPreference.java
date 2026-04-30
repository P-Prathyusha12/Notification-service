package com.example.ns.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    @Column(name = "email_enabled")
    private boolean emailEnabled = false;
    
    @Column(name = "sms_enabled")
    private boolean smsEnabled = false;
    
    @Column(name = "push_enabled")
    private boolean pushEnabled = false;
    
    public NotificationPreference() {}
    
    public NotificationPreference(User user) {
        this.user = user;
        this.emailEnabled = false;
        this.smsEnabled = false;
        this.pushEnabled = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isEmailEnabled() {
        return emailEnabled;
    }
    
    public void setEmailEnabled(boolean emailEnabled) {
        this.emailEnabled = emailEnabled;
    }
    
    public boolean isSmsEnabled() {
        return smsEnabled;
    }
    
    public void setSmsEnabled(boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }
    
    public boolean isPushEnabled() {
        return pushEnabled;
    }
    
    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }
}