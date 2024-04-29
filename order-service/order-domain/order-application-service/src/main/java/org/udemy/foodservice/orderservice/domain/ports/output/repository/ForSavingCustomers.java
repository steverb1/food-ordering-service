package org.udemy.foodservice.orderservice.domain.ports.output.repository;

import org.udemy.foodservice.orderservice.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface ForSavingCustomers {
    Optional<Customer> findCustomer(UUID customerId);
}
