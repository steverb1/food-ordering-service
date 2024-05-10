package org.udemy.foodservice.orderservice.domain;

import org.junit.jupiter.api.Test;
import org.udemy.foodservice.domain.valueobject.*;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.Customer;
import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.entity.Product;
import org.udemy.foodservice.orderservice.domain.entity.Restaurant;
import org.udemy.foodservice.orderservice.domain.mapper.OrderDataMapper;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCreatedPaymentRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderApplicationServiceTestNoMocks {
    @Test
    void createOrderPersistsOrderCorrectly() {
        ForProcessingOrders orderDomainService = new OrderDomainService();
        OrderRepositoryFake orderRepository = new OrderRepositoryFake();
        OrderDataMapper orderDataMapper = new OrderDataMapper();

        UUID customerId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(new CustomerId(customerId));
        CustomerRepositoryFake customerRepository = new CustomerRepositoryFake();
        customerRepository.setCustomer(customer);

        Restaurant restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantId))
                .products(List.of(new Product(new ProductId(productId), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(productId), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();
        RestaurantRepositoryFake restaurantRepository = new RestaurantRepositoryFake();
        restaurantRepository.setRestaurant(restaurant);

        OrderCreateHelper orderCreateHelper = new OrderCreateHelper(orderDomainService, orderRepository, customerRepository, restaurantRepository, orderDataMapper);
        ForPublishingOrderCreatedPaymentRequest orderCreatedPaymentRequestMessagePublisher = new OrderCreatedPaymentRequestMessagePublisherFake();
        OrderApplicationService service = new OrderApplicationService(
                orderCreateHelper,
                orderDataMapper,
                orderCreatedPaymentRequestMessagePublisher,
                orderRepository);

        BigDecimal price = new BigDecimal("200.00");

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
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }
}
