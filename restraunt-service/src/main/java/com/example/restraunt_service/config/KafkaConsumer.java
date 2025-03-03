package com.example.restraunt_service.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-events", groupId = "restaurant-service-group")
    public void consume(String orderEvent) {
        kafkaTemplate.send("restaurant-assigned-topic", orderEvent);
        System.out.println("Received Order Event: " + orderEvent);
    }


}
