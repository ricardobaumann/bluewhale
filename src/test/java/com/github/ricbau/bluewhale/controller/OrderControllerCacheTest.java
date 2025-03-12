package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.TestcontainersConfiguration;
import com.github.ricbau.bluewhale.config.jwt.JwtRepo;
import com.github.ricbau.bluewhale.entities.Order;
import com.github.ricbau.bluewhale.service.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerCacheTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtRepo jwtRepo;
    @MockitoBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCacheResponse() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(orderService.findInDept(id))
                .thenReturn(Optional.of(Order.builder().id(id).build()));

        mockMvc.perform(get("/v1/orders/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtRepo.generateToken("user")))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/orders/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtRepo.generateToken("user")))
                .andExpect(status().isOk());

        verify(orderService, atMost(1)).findInDept(id);
    }
}