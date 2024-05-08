package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
public class OrderCreateHelper {
    private final ForProcessingOrders orderDomainService;
    private final ForSavingOrders orderRepository;
    private final ForSavingCustomers customerRepository;
    private final ForSavingRestaurants restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(ForProcessingOrders orderDomainService,
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
    public OrderCreatedEvent persistOrder(Order order) {
        checkCustomer(order.getCustomerId().getValue());
        Restaurant restaurant = checkRestaurant(order);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private Restaurant checkRestaurant(Order order) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(order);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurant(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", order.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
                    order.getRestaurantId());
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
