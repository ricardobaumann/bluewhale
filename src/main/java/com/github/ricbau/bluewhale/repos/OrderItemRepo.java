package com.github.ricbau.bluewhale.repos;

import com.github.ricbau.bluewhale.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepo extends CrudRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID id);
}
