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





@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/{orderId}")
    public String createDelivery(@PathVariable Long orderId) {
        System.out.println("jejejeje");
        deliveryService.createDelivery(orderId);
        return "ok";
    }

    @GetMapping("/enCamino")
    public List<DeliveryDTO> getCurrentDeliverysByUser() {
        DeliveryStatus status = DeliveryStatus.EN_CAMINO;
        return deliveryService.getCurrentDeliverysByUserAndStatus(status);
    }
    
    @GetMapping("/Completado")
    public List<DeliveryDTO> getCurrentDeliverysByUserAndCompleted() {
        DeliveryStatus status = DeliveryStatus.COMPLETADO;
        return deliveryService.getCurrentDeliverysByUserAndStatus(status);
    }
    
    
}
