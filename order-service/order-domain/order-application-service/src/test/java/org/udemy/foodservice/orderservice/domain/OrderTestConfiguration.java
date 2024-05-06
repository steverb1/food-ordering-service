package org.udemy.foodservice.orderservice.domain;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCancelledPaymentRequest;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.payment.ForPublishingOrderCreatedPaymentRequest;
import org.udemy.foodservice.orderservice.domain.ports.output.messagepublisher.restaurantapproval.ForPublishingOrderPaidRestaurantRequest;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingOrders;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

@SpringBootApplication(scanBasePackages = "org.udemy.foodservice")
public class OrderTestConfiguration {
    @Bean
    public ForPublishingOrderCreatedPaymentRequest orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(ForPublishingOrderCreatedPaymentRequest.class);
    }

    @Bean
    public ForPublishingOrderCancelledPaymentRequest orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(ForPublishingOrderCancelledPaymentRequest.class);
    }

    @Bean
    public ForPublishingOrderPaidRestaurantRequest orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(ForPublishingOrderPaidRestaurantRequest.class);
    }

    @Bean
    public ForSavingOrders orderRepository() {
        return Mockito.mock(ForSavingOrders.class);
    }

    @Bean
    public ForSavingCustomers customerRepository() {
        return Mockito.mock(ForSavingCustomers.class);
    }

    @Bean
    public ForSavingRestaurants restaurantRepository() {
        return Mockito.mock(ForSavingRestaurants.class);
    }

    @Bean
    public ForProcessingOrders orderDomainService() {
        return new OrderDomainService();
    }
}
