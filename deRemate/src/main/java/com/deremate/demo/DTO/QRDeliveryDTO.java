package com.deremate.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QRDeliveryDTO {
    private Long deliveryId;
    private String client;
    private String address;
    private String packageLocation;
}

