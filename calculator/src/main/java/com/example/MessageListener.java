package com.example;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "helloQueue")
    public void handleMessage(String message) {
        System.out.println("Received message: " + message);
    }

}
