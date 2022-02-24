package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TodoInMemoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoInMemoryApplication.class, args);
	}

}
