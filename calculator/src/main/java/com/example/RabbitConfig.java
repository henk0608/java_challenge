package com.example;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue calculatorQueue() {
        return new Queue("calculatorQueue", false);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("replyQueue", false);
    }
}
