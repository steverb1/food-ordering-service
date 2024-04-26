package org.udemy.foodservice.orderservice.domain.event;

import org.udemy.foodservice.domain.event.DomainEvent;
import org.udemy.foodservice.orderservice.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
