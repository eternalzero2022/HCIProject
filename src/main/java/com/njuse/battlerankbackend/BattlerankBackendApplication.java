package com.njuse.battlerankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BattlerankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlerankBackendApplication.class, args);
	}

}
