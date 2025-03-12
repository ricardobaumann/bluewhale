package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.input.OrderInput;
import com.github.ricbau.bluewhale.mappers.OrderMapper;
import com.github.ricbau.bluewhale.output.OrderOutput;
import com.github.ricbau.bluewhale.output.ResourceRef;
import com.github.ricbau.bluewhale.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceRef post(@RequestBody @Valid OrderInput orderInput) {
        return new ResourceRef(orderService.create(orderInput).getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutput> get(@PathVariable UUID id) {
        return orderService.findInDept(id)
                .map(orderMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
