package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.example.demo.config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class ManageAvailabilityTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageAvailabilityTimeApplication.class, args);
	}

}
