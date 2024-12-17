package com.jetbrains.intellij.controller;

import com.jetbrains.intellij.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        kafkaService.sendMessage(message);
        return "Message sent to Kafka!";
    }
}