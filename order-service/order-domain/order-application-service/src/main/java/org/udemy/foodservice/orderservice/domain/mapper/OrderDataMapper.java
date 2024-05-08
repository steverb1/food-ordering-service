package org.udemy.foodservice.orderservice.domain.mapper;

import org.springframework.stereotype.Component;
import org.udemy.foodservice.domain.valueobject.ProductId;
import org.udemy.foodservice.domain.valueobject.RestaurantId;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.entity.Product;
import org.udemy.foodservice.orderservice.domain.entity.Restaurant;

import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(Order order) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(order.getRestaurantId().getValue()))
                .products(order.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProduct().getId().getValue())))
                .collect(Collectors.toList())
                )
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

}
