package org.example.flavorfusion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"org.example.repository","org.example.controller", "org.example.service", "org.example.configs"})
public class FlavorfusionApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlavorfusionApplication.class, args);
	}

}
