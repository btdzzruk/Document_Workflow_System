package com.example.kafka.producer;

import com.example.kafka.event.DocumentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "document_event";

    public void send(DocumentEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}
