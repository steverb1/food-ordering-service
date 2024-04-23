package org.udemy.foodservice.orderservice.domain.valueobject;

import org.udemy.foodservice.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
