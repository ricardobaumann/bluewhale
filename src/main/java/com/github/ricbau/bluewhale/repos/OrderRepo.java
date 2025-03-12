package com.github.ricbau.bluewhale.repos;

import com.github.ricbau.bluewhale.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends CrudRepository<Order, UUID> {
}
