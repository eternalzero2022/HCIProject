package com.njuse.battlerankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableAsync
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60, redisNamespace = "session")
public class BattlerankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlerankBackendApplication.class, args);
	}

}
