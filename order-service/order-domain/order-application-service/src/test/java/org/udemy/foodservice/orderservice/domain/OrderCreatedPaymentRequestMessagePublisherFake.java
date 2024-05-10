package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.orderservice.domain.event.OrderCreatedEvent;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCreatedPaymentRequest;

public class OrderCreatedPaymentRequestMessagePublisherFake implements ForPublishingOrderCreatedPaymentRequest {
    @Override
    public void publish(OrderCreatedEvent domainEvent) {

    }
}
