package com.github.ricbau.bluewhale.output;

import com.github.ricbau.bluewhale.entities.OrderStatus;

import java.util.List;
import java.util.UUID;

public record OrderOutput(
        UUID id,
        OrderStatus status,
        List<OrderItemOutput> items
) {
}
