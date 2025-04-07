package com.deremate.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.entity.Delivery;
import com.deremate.demo.entity.DeliveryStatus;
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

    @Override
    public List<DeliveryDTO> getCurrentDeliverysByUserAndStatus(DeliveryStatus status) {
        User user = userService.getCurrentUser();
        List<Delivery> deliveries = deliveryRepository.findByUserAndStatus(user, status);
        
        return deliveries.stream()
        .map(delivery -> DeliveryDTO.builder()
                .id(delivery.getId())
                .order(delivery.getOrder())
                .startTime(delivery.getStartTime())
                .endTime(delivery.getEndTime())
                .status(delivery.getStatus())
                .deliveryTime(
                    delivery.getEndTime() != null
                        ? formatDuration(Duration.between(delivery.getStartTime(), delivery.getEndTime()))
                        : "N/A"
                )
                .build())
        .collect(Collectors.toList());
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }    
    
}
