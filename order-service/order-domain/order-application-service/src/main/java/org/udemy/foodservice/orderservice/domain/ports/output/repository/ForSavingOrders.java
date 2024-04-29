package org.udemy.foodservice.orderservice.domain.ports.output.repository;

import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.valueobject.TrackingId;

import java.util.Optional;

public interface ForSavingOrders {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
