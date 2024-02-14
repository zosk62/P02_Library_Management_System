package com.midproject.embackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class EmbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmbackendApplication.class, args);
	}

}
