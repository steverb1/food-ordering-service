package org.udemy.foodservice.orderservice.domain.ports.output.repository;

import org.udemy.foodservice.orderservice.domain.entity.Restaurant;

import java.util.Optional;
import java.util.UUID;

public interface ForSavingRestaurants {
    Optional<Restaurant> findRestaurant(UUID restaurantId);
}
