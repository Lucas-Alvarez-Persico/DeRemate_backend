package com.deremate.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.deremate.demo.service.QrService;
import com.deremate.demo.service.Interface.DeliveryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import com.deremate.demo.DTO.DeliveryDTO;
import com.deremate.demo.DTO.QRDeliveryDTO;
import com.deremate.demo.ErrorResponse.ErrorResponse;
import com.deremate.demo.entity.Delivery;
import com.deremate.demo.entity.DeliveryStatus;
import com.deremate.demo.entity.Order;
import com.deremate.demo.repository.DeliveryRepository;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    QrService qrService;

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

    @GetMapping(value = "entregas/{id}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generarQrEntrega(@PathVariable Long id) {
        Delivery delivery = deliveryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Order order = delivery.getOrder();

        QRDeliveryDTO dto = QRDeliveryDTO.builder()
                .deliveryId(delivery.getId())
                .client(order.getClient())
                .address(order.getAddress())
                .packageLocation(order.getPackageLocation())
                .build();

        try {
            byte[] qr = qrService.generarQrDesdeDto(dto);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qr);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
