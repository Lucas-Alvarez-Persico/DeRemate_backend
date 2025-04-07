package com.deremate.demo.service.Interface;

import java.util.List;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.entity.DeliveryStatus;

public interface DeliveryService {

    public void createDelivery(Long orderId);

    public List<DeliveryDTO> getCurrentDeliverysByUserAndStatus(DeliveryStatus status);

}
