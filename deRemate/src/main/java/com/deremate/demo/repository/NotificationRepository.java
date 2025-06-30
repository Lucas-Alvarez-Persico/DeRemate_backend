package com.deremate.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deremate.demo.entity.Notification;
import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
    List<Notification> getAllByRead(Boolean read);
}
