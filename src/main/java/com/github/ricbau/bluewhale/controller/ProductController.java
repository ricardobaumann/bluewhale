package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.mappers.ProductMapper;
import com.github.ricbau.bluewhale.output.ProductOutput;
import com.github.ricbau.bluewhale.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Retrieve all products")
    @GetMapping
    public List<ProductOutput> get() {
        return StreamSupport.stream(productService.findAll().spliterator(), false)
                .map(productMapper::map)
                .collect(Collectors.toList());
    }

}
