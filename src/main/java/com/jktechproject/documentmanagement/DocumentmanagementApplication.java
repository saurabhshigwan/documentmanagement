package com.jktechproject.documentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DocumentmanagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(DocumentmanagementApplication.class, args);
	}

}
