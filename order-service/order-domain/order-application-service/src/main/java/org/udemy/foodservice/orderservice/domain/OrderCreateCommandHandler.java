package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderCommand;
import org.udemy.foodservice.orderservice.domain.dto.create.CreateOrderResponse;
import org.udemy.foodservice.orderservice.domain.entity.Customer;
import org.udemy.foodservice.orderservice.domain.entity.Order;
import org.udemy.foodservice.orderservice.domain.entity.Restaurant;
import org.udemy.foodservice.orderservice.domain.event.OrderCreatedEvent;
import org.udemy.foodservice.orderservice.domain.exception.OrderDomainException;
import org.udemy.foodservice.orderservice.domain.mapper.OrderDataMapper;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderDomainService orderDomainService;
    private final ForSavingOrders orderRepository;
    private final ForSavingCustomers customerRepository;
    private final ForSavingRestaurants restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService,
                                     ForSavingOrders orderRepository,
                                     ForSavingCustomers customerRepository,
                                     ForSavingRestaurants restaurantRepository,
                                     OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = saveOrder(order);
        log.info("Order is created with id: {}", orderResult.getId().getValue());
        return orderDataMapper.orderToCreateOrderResponse(orderResult);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurant(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
                    createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customerId);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
