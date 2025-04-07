package com.deremate.demo.DTO;

import java.time.LocalDateTime;

import com.deremate.demo.entity.DeliveryStatus;
import com.deremate.demo.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private Long id;
    private Order order;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String deliveryTime;
    private DeliveryStatus status;
}
