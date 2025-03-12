package com.github.ricbau.bluewhale.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemInput(
        @NotNull UUID productId,
        @NotNull @Positive BigDecimal amount
) {
}
