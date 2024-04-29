package org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.restaurantapproval;

import org.udemy.foodservice.domain.event.publisher.ForPublishingDomainEvents;
import org.udemy.foodservice.orderservice.domain.event.OrderPaidEvent;

public interface ForPublishingOrderPaidRestaurantRequest extends ForPublishingDomainEvents<OrderPaidEvent> {
}
