package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.orderservice.domain.mapper.OrderDataMapper;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

public class OrderCreateHelperFake extends OrderCreateHelper {
    public OrderCreateHelperFake(ForProcessingOrders orderDomainService, ForSavingOrders orderRepository, ForSavingCustomers customerRepository, ForSavingRestaurants restaurantRepository, OrderDataMapper orderDataMapper) {
        super(orderDomainService, orderRepository, customerRepository, restaurantRepository, orderDataMapper);
    }
}
