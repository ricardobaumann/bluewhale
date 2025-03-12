package com.github.ricbau.bluewhale.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderInput(
        @NotNull @NotEmpty List<@Valid OrderItemInput> items
) {
}
