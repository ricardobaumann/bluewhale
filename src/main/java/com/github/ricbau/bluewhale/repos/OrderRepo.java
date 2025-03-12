package com.github.ricbau.bluewhale.repos;

import com.github.ricbau.bluewhale.entities.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends CrudRepository<Order, UUID> {

    @Query("""
            select o from Order o
                left join fetch o.items items
                    left join fetch items.product
            where o.id = :id
            """)
    Optional<Order> findInDept(UUID id);
}
