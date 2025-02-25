package it.customfanta.be.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "it.customfanta.be.repository")
@ComponentScan(basePackages = "it.customfanta.be")
public class CustomfantaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomfantaBeApplication.class, args);
	}

}
