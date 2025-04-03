package com.deremate.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateOrderDTO {
    
    private String client;

    private String address;

    private String  packageLocation;

}
