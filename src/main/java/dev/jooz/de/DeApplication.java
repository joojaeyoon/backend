package dev.jooz.de;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeApplication.class, args);
	}
	// TODO Dockerize Spring Boot, MySQL

	// TODO DB migrations with Flyway

	// TODO Create Dummy Data

}
