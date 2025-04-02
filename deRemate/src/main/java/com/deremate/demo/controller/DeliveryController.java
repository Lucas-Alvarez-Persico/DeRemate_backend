package com.deremate.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.deremate.demo.service.Interface.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    
}
