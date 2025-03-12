package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.input.OrderInput;
import com.github.ricbau.bluewhale.output.ResourceRef;
import com.github.ricbau.bluewhale.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceRef post(@RequestBody @Valid OrderInput orderInput) {
        return new ResourceRef(orderService.create(orderInput).getId());
    }

}
