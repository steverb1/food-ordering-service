package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.orderservice.domain.entity.Customer;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingCustomers;

import java.util.Optional;
import java.util.UUID;

public class CustomerRepositoryFake implements ForSavingCustomers {
    private Customer customer;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return Optional.of(customer);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
