package com.deremate.demo.repository;

import com.deremate.demo.entity.PushToken;
import com.deremate.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushTokenRepository extends JpaRepository<PushToken, Long> {
    List<PushToken> findByUser(User user);
    List<PushToken> findByToken(String token);
}
