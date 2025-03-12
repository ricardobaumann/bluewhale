package com.github.ricbau.bluewhale.mappers;

import com.github.ricbau.bluewhale.entities.Product;
import com.github.ricbau.bluewhale.output.ProductOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductOutput map(Product product);

}
