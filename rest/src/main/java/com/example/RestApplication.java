package com.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestApplication {

    private static final String QUEUE_NAME = "helloQueue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);

    }

    @GetMapping("/send")
    public String sendMessage() {
        String message = "Hello from REST module!";
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        return "Message sent: " + message;
    }
}
