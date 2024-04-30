package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderCommand;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderQuery;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderResponse;
import org.udemy.foodservice.orderservice.domain.ports.input.service.ForServicingOrders;

@Slf4j
@Validated
@Service
class OrderApplicationService implements ForServicingOrders {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationService(OrderCreateCommandHandler orderCreateCommandHandler,
                                   OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
