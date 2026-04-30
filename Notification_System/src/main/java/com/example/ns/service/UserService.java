package com.example.ns.service;

import com.example.ns.dto.UserRequest;
import com.example.ns.dto.UserResponse;
import com.example.ns.exception.UserNotFoundException;
import com.example.ns.model.NotificationPreference;
import com.example.ns.model.User;
import com.example.ns.repository.PreferenceRepository;
import com.example.ns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @Transactional
    public UserResponse createUser(UserRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        
        User user = new User(request.getName(), request.getEmail(), request.getPhone());
        user = userRepository.save(user);
        
        // Create default preferences (all disabled)
        NotificationPreference preference = new NotificationPreference(user);
        preferenceRepository.save(preference);
        
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), 
                                user.getPhone(), user.getCreatedAt());
    }
    
    // ADD THIS UPDATE METHOD
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        
        // Check if email is being changed and if new email already exists
        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }
        
        // Update user details
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        
        user = userRepository.save(user);
        
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), 
                                user.getPhone(), user.getCreatedAt());
    }
    
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), 
                                user.getPhone(), user.getCreatedAt());
    }
    
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getName(), 
                      user.getEmail(), user.getPhone(), user.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    public User findUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}