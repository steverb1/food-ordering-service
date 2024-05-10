package org.udemy.foodservice.orderservice.domain;

import org.udemy.foodservice.orderservice.domain.entity.Restaurant;
import org.udemy.foodservice.orderservice.domain.ports.output.repository.ForSavingRestaurants;

import java.util.Optional;
import java.util.UUID;

public class RestaurantRepositoryFake implements ForSavingRestaurants {
    private Restaurant restaurant;

    @Override
    public Optional<Restaurant> findRestaurant(UUID restaurantId) {
        return Optional.of(restaurant);
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
