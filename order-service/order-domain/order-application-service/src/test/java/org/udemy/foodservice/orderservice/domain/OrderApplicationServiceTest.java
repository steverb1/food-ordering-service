package org.udemy.foodservice.orderservice.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.udemy.foodservice.domain.valueobject.*;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.*;
import org.udemy.foodservice.orderservice.domain.exception.OrderDomainException;
import org.udemy.foodservice.orderservice.domain.mapper.OrderDataMapper;
import org.udemy.foodservice.orderservice.domain.ports.input.service.ForServicingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {
    @Autowired
    private ForServicingOrders orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private ForSavingOrders orderRepository;

    @Autowired
    private ForSavingCustomers customerRepository;

    @Autowired
    ForSavingRestaurants restaurantRepository;

    private final UUID CUSTOMER_ID = UUID.fromString("903d3aba-32fe-4fc5-bac7-9cf05a141b71");
    private final UUID RESTAURANT_ID = UUID.fromString("a2e73542-7add-4630-91c8-cc8e7f512bf9");
    private final UUID PRODUCT_ID = UUID.fromString("bd3bec64-915b-4f77-8ef6-45284feb6076");
    private final UUID ORDER_ID = UUID.fromString("0ac6eaad-b7dd-48ee-9a86-86e14acc2d7b");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();

        Order order = Order.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .price(new Money(PRICE))
                .build();
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurant(Restaurant.builder()
                        .restaurantId(new RestaurantId(RESTAURANT_ID))
                .build()))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder() {
        Order order = Order.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .price(new Money(PRICE))
                .build();
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(order);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        Order order = Order.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .price(new Money(new BigDecimal("250.00")))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .build();;
        OrderDomainException orderDomainException =  assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(order));
        assertEquals("Total price: 250.00 is not equal to Order items total: 200.00!",
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        Order order = Order.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .price(new Money(new BigDecimal("210.00")))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("60.00")))
                                .subTotal(new Money(new BigDecimal("60.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .build();
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(order));
        assertEquals("Order item price: 60.00 is not valid for product " + PRODUCT_ID,
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassiveRestaurant() {
        Order order = Order.builder()
                .customerId(new CustomerId(CUSTOMER_ID))
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .items(List.of(OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(1)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("50.00")))
                                .build(),
                        OrderItem.builder()
                                .product(new Product(new ProductId(PRODUCT_ID)))
                                .quantity(3)
                                .price(new Money(new BigDecimal("50.00")))
                                .subTotal(new Money(new BigDecimal("150.00")))
                                .build()))
                .price(new Money(PRICE))
                .build();
        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(order.getRestaurantId().getValue()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(false)
                .build();
        when(restaurantRepository.findRestaurant(orderDataMapper.createOrderCommandToRestaurant(order)))
                .thenReturn(Optional.of(restaurantResponse));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> orderApplicationService.createOrder(order));
        assertEquals("Restaurant with id " + RESTAURANT_ID + " is currently not active!",
                orderDomainException.getMessage());
    }
}
