package com.deremate.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deremate.demo.entity.Delivery;
import com.deremate.demo.entity.Order;
import com.deremate.demo.entity.User;
import com.deremate.demo.repository.DeliveryRepository;
import com.deremate.demo.service.Interface.DeliveryService;
import com.deremate.demo.service.Interface.OrderService;
import com.deremate.demo.service.Interface.UserService;


@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Override
    public void createDelivery(Long orderId) {
        Delivery delivery = new Delivery();
        User user = userService.getCurrentUser();
        Order order = orderService.getOrderById(orderId);
        delivery.setUser(user);
        delivery.setOrder(order);

        if (orderService.changeState(order)) {        
            deliveryRepository.save(delivery);
        }
    }
    
}
