package com.example.ns.service;

import com.example.ns.dto.PreferenceRequest;
import com.example.ns.exception.UserNotFoundException;
import com.example.ns.model.NotificationPreference;
import com.example.ns.model.User;
import com.example.ns.repository.PreferenceRepository;
import com.example.ns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreferenceService {
    
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public NotificationPreference updatePreferences(Long userId, PreferenceRequest request) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        // Find preference or create new one
        NotificationPreference preference = preferenceRepository.findByUser(user).orElse(null);
        
        if (preference == null) {
            preference = new NotificationPreference(user);
            preference = preferenceRepository.save(preference);
        }
        
        // Update preferences
        if (request.getEmailEnabled() != null) {
            preference.setEmailEnabled(request.getEmailEnabled());
        }
        if (request.getSmsEnabled() != null) {
            preference.setSmsEnabled(request.getSmsEnabled());
        }
        if (request.getPushEnabled() != null) {
            preference.setPushEnabled(request.getPushEnabled());
        }
        
        // Save and return
        return preferenceRepository.save(preference);
    }
    
    public NotificationPreference getPreferences(Long userId) {
        // Find user or throw exception
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        // Find preference or create new one
        NotificationPreference preference = preferenceRepository.findByUser(user).orElse(null);
        
        if (preference == null) {
            preference = new NotificationPreference(user);
            preference = preferenceRepository.save(preference);
        }
        
        return preference;
    }
}