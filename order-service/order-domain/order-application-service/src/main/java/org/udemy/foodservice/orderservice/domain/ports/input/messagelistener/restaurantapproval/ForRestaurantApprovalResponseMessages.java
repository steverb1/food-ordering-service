package org.udemy.foodservice.orderservice.domain.ports.input.messagelistener.restaurantapproval;

import org.udemy.foodservice.orderservice.domain.dto.message.RestaurantApprovalResponse;

public interface ForRestaurantApprovalResponseMessages {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
