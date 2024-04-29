package org.udemy.foodservice.domain.event.publisher;

import org.udemy.foodservice.domain.event.DomainEvent;

public interface ForPublishingDomainEvents<T extends DomainEvent> {
    void publish(T domainEvent);
}
