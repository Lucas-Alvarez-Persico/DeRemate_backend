package com.deremate.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.deremate.demo.service.Interface.DeliveryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.entity.DeliveryStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/{status}")
    public List<DeliveryDTO> getCurrentDeliverysByUser(@PathVariable String status) {
        DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(status.toUpperCase());
        return deliveryService.getCurrentDeliverysByUserAndStatus(deliveryStatus);
    }
    
    @PutMapping("/{deliveryId}")
    public String assingDelivery(@PathVariable Long deliveryId) {
        return deliveryService.assingDelivery(deliveryId);
    }
    
    @PutMapping("completed/{deliveryId}")
    public String putMethodName(@PathVariable Long deliveryId) {
        return deliveryService.completeDelivery(deliveryId);
    }
}
