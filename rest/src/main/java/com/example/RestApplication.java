package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RestApplication {

    private static final String QUEUE_NAME = "calculatorQueue";
    private static final String REPLY_QUEUE_NAME = "replyQueue";
    private String result;
    private CompletableFuture<String> resultFuture = new CompletableFuture<>();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);

    }

    /*@GetMapping("/send")
    public String sendMessage() {
        String message = "Hello from REST module!";
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        return "Message sent: " + message;
    }*/
    @GetMapping("/sum")
    public CalculationResult sum(@RequestParam double a, @RequestParam double b) {
        String operation = a + " + " + b;
        rabbitTemplate.convertAndSend(QUEUE_NAME, operation);

        // Wait for the response from the calculator module
        return new CalculationResult(formatResult(waitForResult()));
    }

    @GetMapping("/subtract")
    public CalculationResult subtract(@RequestParam double a, @RequestParam double b) {
        String operation = a + " - " + b;
        rabbitTemplate.convertAndSend(QUEUE_NAME, operation);

        // Wait for the response from the calculator module
        return new CalculationResult(formatResult(waitForResult()));
    }

    @GetMapping("/multiply")
    public CalculationResult multiply(@RequestParam double a, @RequestParam double b) {
        String operation = a + " * " + b;
        rabbitTemplate.convertAndSend(QUEUE_NAME, operation);

        // Wait for the response from the calculator module
        return new CalculationResult(formatResult(waitForResult()));
    }

    @GetMapping("/divide")
    public CalculationResult divide(@RequestParam double a, @RequestParam double b) {
        String operation = a + " / " + b;
        //check if b is 0
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        rabbitTemplate.convertAndSend(QUEUE_NAME, operation);

        // Wait for the response from the calculator module
        return new CalculationResult(formatResult(waitForResult()));

    }

    // Listen to the replyQueue to get the result
    @RabbitListener(queues = REPLY_QUEUE_NAME)
    public void receiveResult(String message) {
        resultFuture.complete(message);
    }

    /*private String waitForResult() {
        while (result == null) {
            // Busy wait for result (This is for simplicity, consider better approaches like CompletableFutures)
            System.out.println("Waiting for result...");
        }
        String finalResult = result;
        result = null; // Reset result for next calculation
        return finalResult;
    }*/
    private String waitForResult() {
        try {
            result = resultFuture.get(); // Wait for the result asynchronously
            resultFuture = new CompletableFuture<>(); // Reset for the next calculation
            return result;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to get result", e);
        }
    }

    // Format result to return either an integer or a double
    private Object formatResult(String result) {
        double numericResult = Double.parseDouble(result);
        if (numericResult == (int) numericResult) {
            // If the result is a whole number, return it as an integer
            return (int) numericResult;
        } else {
            // Return as a double for decimal values
            return numericResult;
        }
    }
}
