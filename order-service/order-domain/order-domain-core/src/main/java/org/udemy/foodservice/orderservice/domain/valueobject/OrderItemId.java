package org.udemy.foodservice.orderservice.domain.valueobject;

import org.udemy.foodservice.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
