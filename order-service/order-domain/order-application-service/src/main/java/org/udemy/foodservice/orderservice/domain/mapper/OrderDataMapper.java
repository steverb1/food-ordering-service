package org.udemy.foodservice.orderservice.domain.mapper;

import org.springframework.stereotype.Component;
import org.udemy.foodservice.domain.valueobject.CustomerId;
import org.udemy.foodservice.domain.valueobject.Money;
import org.udemy.foodservice.domain.valueobject.ProductId;
import org.udemy.foodservice.domain.valueobject.RestaurantId;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderCommand;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.dto.create.OrderAddress;
import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.entity.OrderItem;
import org.udemy.foodservice.orderservice.domain.entity.Product;
import org.udemy.foodservice.orderservice.domain.entity.Restaurant;
import org.udemy.foodservice.orderservice.domain.valueobject.StreetAddress;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                .collect(Collectors.toList())
                )
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getRestaurantId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<org.udemy.foodservice.orderservice.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> OrderItem.builder()
                        .product(new Product(new ProductId(orderItem.getProductId())))
                        .price(new Money(orderItem.getPrice()))
                        .quantity(orderItem.getQuantity())
                        .subTotal(new Money(orderItem.getSubTotal()))
                        .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
