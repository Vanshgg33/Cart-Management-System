package com.spring.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.spring.repo")
@EntityScan("com.spring.model")
@ComponentScan(basePackages = "com.spring")
public class CartManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication .run(CartManagementSystemApplication.class, args);
	}

}
