package org.udemy.foodservice.orderservice.domain.ports.output.repository;

import org.udemy.foodservice.orderservice.domain.entity.Restaurant;

import java.util.Optional;

public interface ForSavingRestaurants {
    Optional<Restaurant> findRestaurant(Restaurant restaurant);
}
