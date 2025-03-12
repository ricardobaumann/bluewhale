package com.github.ricbau.bluewhale.service;

import com.github.ricbau.bluewhale.entities.Order;
import com.github.ricbau.bluewhale.entities.OrderItem;
import com.github.ricbau.bluewhale.exceptions.InvalidResourceReference;
import com.github.ricbau.bluewhale.input.OrderItemInput;
import com.github.ricbau.bluewhale.repos.OrderItemRepo;
import com.github.ricbau.bluewhale.repos.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemService {
    private final OrderItemRepo orderItemRepo;
    private final ProductRepo productRepo;

    public void createItemOn(final OrderItemInput orderItemInput, final Order order) {
        orderItemRepo.save(
                OrderItem.builder()
                        .id(UUID.randomUUID())
                        .product(productRepo.findById(orderItemInput.productId())
                                .orElseThrow(() -> new InvalidResourceReference("product", orderItemInput.productId())))
                        .amount(orderItemInput.amount())
                        .order(order)
                        .build());
    }
}
