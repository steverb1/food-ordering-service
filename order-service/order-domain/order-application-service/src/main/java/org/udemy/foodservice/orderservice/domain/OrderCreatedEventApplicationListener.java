package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.udemy.foodservice.orderservice.domain.event.OrderCreatedEvent;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCreatedPaymentRequest;

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {
    private final ForPublishingOrderCreatedPaymentRequest orderCreatedPaymentRequestMessagePublisher;

    public OrderCreatedEventApplicationListener(ForPublishingOrderCreatedPaymentRequest orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    @TransactionalEventListener
    void process(OrderCreatedEvent orderCreatedEvent) {
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    }
}
