package org.udemy.foodservice.orderservice.domain.ports.input.service;

import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderCommand;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderQuery;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface ForServicingOrders {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
