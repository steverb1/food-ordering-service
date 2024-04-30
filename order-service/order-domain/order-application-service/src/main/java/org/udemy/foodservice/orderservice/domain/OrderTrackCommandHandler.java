package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderQuery;
import org.udemy.foodservice.orderservice.domain.dto.track.TrackOrderResponse;

@Slf4j
@Component
public class OrderTrackCommandHandler {
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
