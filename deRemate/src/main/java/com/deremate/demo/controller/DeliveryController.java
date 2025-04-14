package com.deremate.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.deremate.demo.service.Interface.DeliveryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.ErrorResponse.ErrorResponse;
import com.deremate.demo.entity.DeliveryStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/{status}")
    public List<DeliveryDTO> getCurrentDeliverysByUser(@PathVariable String status) {
        DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(status.toUpperCase());
        return deliveryService.getCurrentDeliverysByUserAndStatus(deliveryStatus);
    }
    
    @PutMapping("/{deliveryId}")
    public ResponseEntity<?> assingDelivery(@PathVariable Long deliveryId) {
        try{
            return ResponseEntity.ok(deliveryService.assingDelivery(deliveryId));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

        }
    }
    
    @PutMapping("completed/{deliveryId}/{code}")
    public ResponseEntity<?> putMethodName(@PathVariable Long deliveryId, @PathVariable String code) {
        try{
            return ResponseEntity.ok(deliveryService.completeDelivery(deliveryId, code));
        }catch(RuntimeException e){
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

        }
    }
}
