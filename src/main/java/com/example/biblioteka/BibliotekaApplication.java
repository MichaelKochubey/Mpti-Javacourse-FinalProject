package com.example.biblioteka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotekaApplication {
	@Value("${context.path:/api/v1}")
	String contextPath;

	public static void main(String[] args) {
		SpringApplication.run(BibliotekaApplication.class, args);
	}

}
