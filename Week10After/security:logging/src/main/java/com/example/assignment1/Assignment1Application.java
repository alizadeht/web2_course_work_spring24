package com.example.assignment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot application entry point
 */
@SpringBootApplication
public class Assignment1Application {

	/**
	 * default constructor
	 */
	public Assignment1Application() {
		super();
	}
	/**
	 * core method for running -> SpringBoot application.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Assignment1Application.class, args);
	}

}
