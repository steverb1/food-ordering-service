package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.udemy.foodservice.orderservice.domain.dto.message.RestaurantApprovalResponse;
import org.udemy.foodservice.orderservice.domain.ports.input.messagelistener.restaurantapproval.ForRestaurantApprovalResponseMessages;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListener implements ForRestaurantApprovalResponseMessages {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
