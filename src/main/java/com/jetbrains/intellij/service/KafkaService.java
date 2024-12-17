package com.jetbrains.intellij.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private static final String TOPIC = "topic1";
    private static final String GroupId = "example-group";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Kafka Producer - Sender besked
    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent message: " + message);
    }

    // Kafka Consumer - Modtager besked
    @KafkaListener(topics = TOPIC, groupId = GroupId)
    public void consumeMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
