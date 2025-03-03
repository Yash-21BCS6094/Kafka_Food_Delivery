package com.example.delivery_service.config;

import com.example.delivery_service.event.OrderEvent;
import com.example.delivery_service.model.Agent;
import com.example.delivery_service.model.OrderInfo;
import com.example.delivery_service.repository.AgentRepository;
import com.example.delivery_service.repository.OrderInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final AgentRepository agentRepository;
    private final OrderInfoRepository orderInfoRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public KafkaConsumer(ObjectMapper objectMapper,
                         AgentRepository agentRepository,
                         OrderInfoRepository orderInfoRepository,
                         KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.agentRepository = agentRepository;
        this.orderInfoRepository = orderInfoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "restaurant-assigned-topic", groupId = "delivery-service")
    public void consume(String orderEvent) {
        try{
            OrderEvent orderEve = objectMapper.readValue(orderEvent, OrderEvent.class);
            OrderInfo info = new OrderInfo();
            Agent agent = agentRepository.findRandomAgent().get();
            info.setAgentId(agent.getId().toString());
            info.setOrderId(orderEve.getOrderId().toString());
            info.setUserId(orderEve.getUserId().toString());
            info.setId(null);
            kafkaTemplate.send("delivery-assigned-topic", orderEvent);
            orderInfoRepository.save(info);
            System.out.println("Received Order Event: " + orderEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
