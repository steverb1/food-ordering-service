package org.udemy.foodservice.orderservice.domain.ports.input.service;

import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderQuery;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.Order;

import javax.validation.Valid;

public interface ForServicingOrders {
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
    CreateOrderResponse createOrder(Order order);
}
