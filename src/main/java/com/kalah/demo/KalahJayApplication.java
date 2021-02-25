package com.kalah.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.kalah"})
public class KalahJayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahJayApplication.class, args);
	}

}
