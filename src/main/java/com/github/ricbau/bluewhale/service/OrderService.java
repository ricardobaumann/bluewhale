package com.github.ricbau.bluewhale.service;

import com.github.ricbau.bluewhale.entities.Order;
import com.github.ricbau.bluewhale.input.OrderInput;
import com.github.ricbau.bluewhale.repos.OrderRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderItemService orderItemService;

    public Order create(OrderInput orderInput) {

        Order order = orderRepo.save(Order.builder()
                .id(UUID.randomUUID())
                .build());

        orderInput.items()
                .forEach(orderItemInput -> orderItemService.createItemOn(orderItemInput, order));

        return order;
    }

}
