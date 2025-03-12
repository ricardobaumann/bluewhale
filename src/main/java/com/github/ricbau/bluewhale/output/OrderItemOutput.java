package com.github.ricbau.bluewhale.output;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemOutput(
        UUID productId,
        String productName,
        BigDecimal amount
) {
}
