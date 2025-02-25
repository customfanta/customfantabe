package it.customfanta.be.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("it.customfanta.be")
public class CustomfantaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomfantaBeApplication.class, args);
	}

}
