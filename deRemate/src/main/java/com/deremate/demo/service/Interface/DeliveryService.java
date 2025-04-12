package com.deremate.demo.service.Interface;

import java.util.List;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.entity.DeliveryStatus;
import com.deremate.demo.entity.Order;

public interface DeliveryService {

    public void createDelivery(Order order);

    public String assingDelivery (Long deliveryId);

    public String completeDelivery (Long deliveryId);

    public List<DeliveryDTO> getCurrentDeliverysByUserAndStatus(DeliveryStatus status);

}
