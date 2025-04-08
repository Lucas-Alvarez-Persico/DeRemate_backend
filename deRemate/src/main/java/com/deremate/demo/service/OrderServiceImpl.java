package com.deremate.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deremate.demo.entity.Order;
import com.deremate.demo.repository.OrderRepository;
import com.deremate.demo.service.Interface.DeliveryService;
import com.deremate.demo.service.Interface.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryService deliveryService;

    @Override
    public List<Order> getOrders(){
        return orderRepository.findAll();
    };

    @Override
    public void createOrders(String client, String address, String packageLocation) {

        Order order = new Order();
        order.setClient(client);
        order.setAddress(address);
        order.setPackageLocation(packageLocation);
        Order orderSaved = orderRepository.save(order);
        deliveryService.createDelivery(orderSaved);
        
    };

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalStateException("Orden no encontrado."));
    }
    
}
