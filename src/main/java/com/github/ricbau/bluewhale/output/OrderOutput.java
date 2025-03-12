package com.github.ricbau.bluewhale.output;

import java.util.List;
import java.util.UUID;

public record OrderOutput(
        UUID id,
        List<OrderItemOutput> items
) {
}
