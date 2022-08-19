package com.officehours.registry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication

public class RegistryApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}
}
