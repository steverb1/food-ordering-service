package org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment;

import org.udemy.foodservice.domain.event.publisher.ForPublishingDomainEvents;
import org.udemy.foodservice.orderservice.domain.event.OrderCreatedEvent;

public interface ForPublishingOrderCreatedPaymentRequest extends ForPublishingDomainEvents<OrderCreatedEvent> {
}
