package com.example.ns.controller;

import com.example.ns.dto.PreferenceRequest;
import com.example.ns.model.NotificationPreference;
import com.example.ns.service.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class PreferenceController {
    
    @Autowired
    private PreferenceService preferenceService;
    
    @PutMapping("/{userId}/preferences")
    public ResponseEntity<NotificationPreference> updatePreferences(
            @PathVariable Long userId,
            @Valid @RequestBody PreferenceRequest request) {
        NotificationPreference preferences = preferenceService.updatePreferences(userId, request);
        return ResponseEntity.ok(preferences);
    }
    
    @GetMapping("/{userId}/preferences")
    public ResponseEntity<NotificationPreference> getPreferences(@PathVariable Long userId) {
        NotificationPreference preferences = preferenceService.getPreferences(userId);
        return ResponseEntity.ok(preferences);
    }
}