package com.github.ricbau.bluewhale.controller;

import com.github.ricbau.bluewhale.TestcontainersConfiguration;
import com.github.ricbau.bluewhale.config.jwt.JwtRepo;
import com.github.ricbau.bluewhale.repos.OrderRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(scripts = "classpath:populate_orders.sql")
class OrderControllerGetE2ETest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtRepo jwtRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Test
    void shouldGetOrder() throws Exception {
        UUID id = orderRepo.findAll().iterator().next().getId();
        mockMvc.perform(get("/v1/orders/{id}", id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtRepo.generateToken("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.status", is("OPEN")))

                .andExpect(jsonPath("$.items[0].productId", notNullValue()))
                .andExpect(jsonPath("$.items[0].productName", is("foo")))
                .andExpect(jsonPath("$.items[0].amount", is(10.0)))

                .andExpect(jsonPath("$.items[1].productId", notNullValue()))
                .andExpect(jsonPath("$.items[1].productName", is("bar")))
                .andExpect(jsonPath("$.items[1].amount", is(20.0)))

                .andDo(print())

        ;

    }

    @Test
    void shouldReturnNotFoundOnAbsentOrder() throws Exception {
        mockMvc.perform(get("/v1/orders/{id}", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtRepo.generateToken("user")))
                .andExpect(status().isNotFound())
        ;

    }
}