package com.github.ricbau.bluewhale.output;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductOutput(
        UUID id,
        String name,
        BigDecimal unitPrice
) {
}
