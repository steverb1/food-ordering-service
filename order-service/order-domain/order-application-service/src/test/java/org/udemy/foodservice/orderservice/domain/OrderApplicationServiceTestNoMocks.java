package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.domain.valueobject.CustomerId;
import org.udemy.foodservice.domain.valueobject.Money;
import org.udemy.foodservice.domain.valueobject.ProductId;
import org.udemy.foodservice.domain.valueobject.RestaurantId;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.entity.Product;
import org.udemy.foodservice.orderservice.domain.mapper.OrderDataMapper;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCreatedPaymentRequest;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderApplicationServiceTestNoMocks {
    //@Test
    void test() {
        ForProcessingOrders orderDomainService = new OrderDomainService();
        ForSavingOrders orderRepository = null;
        ForSavingCustomers customerRepository = null;
        ForSavingRestaurants restaurantRepository = null;
        OrderDataMapper orderDataMapper = new OrderDataMapper();
        OrderCreateHelper orderCreateHelper = new OrderCreateHelper(orderDomainService, orderRepository, customerRepository, restaurantRepository, orderDataMapper);
        ForPublishingOrderCreatedPaymentRequest orderCreatedPaymentRequestMessagePublisher = null;
        OrderCreateCommandHandler ordercreateCommandHandler = new OrderCreateCommandHandler(orderCreateHelper, orderDataMapper, orderCreatedPaymentRequestMessagePublisher);
        OrderTrackCommandHandler orderTrackCommandHandler = new OrderTrackCommandHandler(orderDataMapper, orderRepository);
        OrderApplicationService service = new OrderApplicationService(ordercreateCommandHandler, orderTrackCommandHandler);

        UUID customerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        BigDecimal price = new BigDecimal(50);

        Order order = Order.builder()
                .customerId(new CustomerId(customerId))
                .restaurantId(new RestaurantId(restaurantId))
                .price(new Money(price))
                .items(List.of(org.udemy.foodservice.orderservice.domain.entity.OrderItem.builder()
                                .product(new Product(new ProductId(productId)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        org.udemy.foodservice.orderservice.domain.entity.OrderItem.builder()
                                .product(new Product(new ProductId(productId)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .build();
        CreateOrderResponse createOrderResponse = service.createOrder(order);
    }
}
