package com.github.ricbau.bluewhale.mappers;

import com.github.ricbau.bluewhale.entities.Order;
import com.github.ricbau.bluewhale.entities.OrderItem;
import com.github.ricbau.bluewhale.output.OrderItemOutput;
import com.github.ricbau.bluewhale.output.OrderOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderOutput map(Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemOutput map(OrderItem orderItem);

}
