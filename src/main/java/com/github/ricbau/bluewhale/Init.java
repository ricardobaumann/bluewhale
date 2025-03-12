package com.github.ricbau.bluewhale;

import com.github.ricbau.bluewhale.entities.Product;
import com.github.ricbau.bluewhale.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@AllArgsConstructor
@Transactional
public class Init implements CommandLineRunner {
    private final ProductService productService;

    @Override
    public void run(final String... args) {
        productService.deleteAll();
        productService.save(Product.builder()
                .id(UUID.randomUUID())
                .name("foo")
                .unitPrice(new BigDecimal(10))
                .build());
        productService.save(Product.builder()
                .id(UUID.randomUUID())
                .name("bar")
                .unitPrice(new BigDecimal(20))
                .build());
    }
}
