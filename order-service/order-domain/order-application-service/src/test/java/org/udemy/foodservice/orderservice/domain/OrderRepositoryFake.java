package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.valueobject.TrackingId;

import java.util.Optional;

public class OrderRepositoryFake implements ForSavingOrders {
    @Override
    public Order save(Order order) {
        return order;
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return Optional.empty();
    }
}
