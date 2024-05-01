package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.udemy.foodservice.domain.event.publisher.ForPublishingDomainEvents;
import org.udemy.foodservice.orderservice.domain.event.OrderCreatedEvent;

@Slf4j
@Component
public class ApplicationDomainEventPublisher implements
        ApplicationEventPublisherAware,
        ForPublishingDomainEvents<OrderCreatedEvent> {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
        log.info("OrderCreatedEvent is published for order id: {}",
                domainEvent.getOrder().getId().getValue());
    }
}
