package com.example.notification_service.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "order-events", groupId = "notification-service-group")
    public void consumeInitiation(String orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);
        System.out.println("ORDER_INITIATED");
    }

    @KafkaListener(topics = "restaurant-assigned-topic", groupId = "notification-service-group")
    public void consumeRestaurant(String orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);
        System.out.println("RESTAURANT_ASSIGNED");
    }

    @KafkaListener(topics = "delivery-assigned-topic", groupId = "notification-service-group")
    public void consumeDelivery(String orderEvent) {
        System.out.println("Received Order Event: " + orderEvent);
        System.out.println("OUT_FOR_DELIVERY");
    }



}