package com.github.ricbau.bluewhale.service;

import com.github.ricbau.bluewhale.repos.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    @Delegate
    private final ProductRepo productRepo;
}
