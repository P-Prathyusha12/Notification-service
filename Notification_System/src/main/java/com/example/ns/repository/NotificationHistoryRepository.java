package com.example.ns.repository;

import java.util.List;

import com.example.ns.model.User;  // Fix this import
import com.example.ns.model.ChannelType;
import com.example.ns.model.NotificationHistory;
import com.example.ns.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {
    List<NotificationHistory> findByUser(User user);
    List<NotificationHistory> findByUserAndStatus(User user, NotificationStatus status);
    List<NotificationHistory> findByUserAndChannelType(User user, ChannelType channelType);
}