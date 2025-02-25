package it.customfanta.be.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("it.customfanta.be")
@EntityScan("it.customfanta.be.model")
@EnableJpaRepositories("it.customfanta.be.repository")
public class CustomfantaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomfantaBeApplication.class, args);
	}

}
