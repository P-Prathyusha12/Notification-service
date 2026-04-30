package com.example.ns.repository;

import com.example.ns.model.NotificationPreference;
import com.example.ns.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    
    // Add this method - it was missing
    Optional<NotificationPreference> findByUser(User user);
}