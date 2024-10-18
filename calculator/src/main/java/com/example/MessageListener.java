package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @RabbitListener(queues = "calculatorQueue")
    public void handleMessage(String message) {

        logger.info("Received message: {}", message);

        String[] parts = message.split(" ");
        double a = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double b = Double.parseDouble(parts[2]);

        double result;

        switch (operator) {
            case "+" ->
                result = a + b;
            case "-" ->
                result = a - b;
            case "*" ->
                result = a * b;
            case "/" -> {
                if (b == 0) {
                    System.out.println("Cannot divide by zero");
                    return;
                }
                result = a / b;
            }
            default -> {
                System.out.println("Unsupported operation: " + operator);
                return;
            }
        }

        // Send the result back to the "rest" module
        rabbitTemplate.convertAndSend("replyQueue", String.valueOf(result));
        logger.info("Result sent back: {}", result);
    }

}
