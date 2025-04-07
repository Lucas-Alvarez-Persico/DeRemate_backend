package com.deremate.demo.service.Interface;

import java.util.List;

import com.deremate.demo.entity.Order;

public interface OrderService {

    public List<Order> getOrders();

    public void createOrders(String client, String address, String packageLocation);

    public Order getOrderById(Long orderId);

    public Boolean changeState(Order order);


} 

