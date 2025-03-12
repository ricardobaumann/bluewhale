package com.github.ricbau.bluewhale.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ricbau.bluewhale.TestcontainersConfiguration;
import com.github.ricbau.bluewhale.config.jwt.JwtRepo;
import com.github.ricbau.bluewhale.entities.Product;
import com.github.ricbau.bluewhale.output.ResourceRef;
import com.github.ricbau.bluewhale.repos.OrderItemRepo;
import com.github.ricbau.bluewhale.repos.ProductRepo;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtRepo jwtRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;

    private final UUID product1Id = UUID.randomUUID();
    private final UUID product2Id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        productRepo.save(Product.builder()
                .id(product1Id)
                .name("foo")
                .unitPrice(new BigDecimal(5))
                .build());

        productRepo.save(Product.builder()
                .id(product2Id)
                .name("bar")
                .unitPrice(new BigDecimal(10))
                .build());
    }

    @Test
    void shouldCreateOrder() throws Exception {
        String body = mockMvc.perform(post("/v1/orders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtRepo.generateToken("user"))
                        .content(String.format("""
                                {
                                    "items": [
                                        {
                                            "productId": "%s",
                                            "amount": 10
                                        },
                                        {
                                            "productId": "%s",
                                            "amount": 2
                                        }
                                    ]
                                }
                                """, product1Id, product2Id))
                ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        ResourceRef resourceRef = objectMapper.readValue(body, ResourceRef.class);

        assertThat(orderItemRepo.findByOrderId(resourceRef.id()))
                .hasSize(2)
                .has(new Condition<>(orderItems -> orderItems.stream()
                        .filter(orderItem -> orderItem.getProduct().getId().equals(product1Id))
                        .anyMatch(orderItem -> orderItem.getAmount().equals(new BigDecimal(10))), "has product 1"))
                .has(new Condition<>(orderItems -> orderItems.stream()
                        .filter(orderItem -> orderItem.getProduct().getId().equals(product2Id))
                        .anyMatch(orderItem -> orderItem.getAmount().equals(new BigDecimal(2))), "has product 2"));
        ;
    }
}