package com.vidaanimal.vidaanimal_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VidaanimalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidaanimalBackendApplication.class, args);
	}

}
