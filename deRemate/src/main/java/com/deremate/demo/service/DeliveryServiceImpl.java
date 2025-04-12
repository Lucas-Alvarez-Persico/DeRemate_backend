package com.deremate.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    OrderService orderService;

    @Override
    public void createDelivery(Order order) {
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        deliveryRepository.save(delivery);
    }

    @Override
    public String assingDelivery (Long deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new IllegalStateException("Delivery no encontrado."));
        User user = userService.getCurrentUser();
        delivery.setUser(user);
        delivery.setStatus(DeliveryStatus.EN_CAMINO);
        delivery.setStartTime(java.time.LocalDateTime.now());
        deliveryRepository.save(delivery);
        return "ok";
    }

    @Override
    public String completeDelivery (Long deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId)
        .orElseThrow(() -> new IllegalStateException("Delivery no encontrado."));
        delivery.setStatus(DeliveryStatus.COMPLETADO);
        delivery.setEndTime(java.time.LocalDateTime.now());
        deliveryRepository.save(delivery);
        return "ok";
    }

    @Override
    public List<DeliveryDTO> getCurrentDeliverysByUserAndStatus(DeliveryStatus status) {
        List<Delivery> deliveries;
        if(status != DeliveryStatus.PENDIENTE){
            User user = userService.getCurrentUser();
            deliveries = deliveryRepository.findByUserAndStatus(user, status);
        }
        else{
            deliveries = deliveryRepository.findByStatus(status);
        }
        
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
