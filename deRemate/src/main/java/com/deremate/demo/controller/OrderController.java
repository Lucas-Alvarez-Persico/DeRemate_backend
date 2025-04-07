package com.deremate.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deremate.demo.DTO.CreateOrderDTO;
import com.deremate.demo.entity.Order;
import com.deremate.demo.service.Interface.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/order")
public class OrderController {
    

    @Autowired
    private OrderService orderService;



    @GetMapping()
    public List<Order> getOrders() {
        return orderService.getOrders();
    }
    
    @PostMapping()
    public String postMethodName(@RequestBody CreateOrderDTO order) {
        orderService.createOrders(order.getClient(), order.getAddress(), order.getPackageLocation());
        return "ok";
    }



}
