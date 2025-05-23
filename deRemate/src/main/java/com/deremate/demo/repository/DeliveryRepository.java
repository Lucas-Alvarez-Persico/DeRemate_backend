package com.deremate.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deremate.demo.entity.Delivery;
import com.deremate.demo.entity.DeliveryStatus;
import com.deremate.demo.entity.User;


@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    List<Delivery> findByUserAndStatus(User user, DeliveryStatus status);

    List<Delivery> findByStatus(DeliveryStatus status);

    Boolean existsByUserAndStatus(User user, DeliveryStatus status);

}
