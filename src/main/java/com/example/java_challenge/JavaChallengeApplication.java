package com.example.java_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JavaChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaChallengeApplication.class, args);
	}

	/*@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}*/

}
