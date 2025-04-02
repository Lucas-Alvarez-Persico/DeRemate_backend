package com.deremate.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deremate.demo.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
}
