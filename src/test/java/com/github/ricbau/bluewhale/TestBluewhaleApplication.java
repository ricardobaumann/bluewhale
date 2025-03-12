package com.github.ricbau.bluewhale;

import org.springframework.boot.SpringApplication;

public class TestBluewhaleApplication {

	public static void main(String[] args) {
		SpringApplication.from(BluewhaleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
