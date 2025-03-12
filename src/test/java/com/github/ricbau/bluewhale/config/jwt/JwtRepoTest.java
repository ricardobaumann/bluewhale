package com.github.ricbau.bluewhale.config.jwt;

import com.github.ricbau.bluewhale.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class JwtRepoTest {
    @Autowired
    private JwtRepo jwtRepo;

    @Test
    void shouldGenerateAndParseToken() {
        jwtRepo.parse(jwtRepo.generateToken("foo"));
    }

}