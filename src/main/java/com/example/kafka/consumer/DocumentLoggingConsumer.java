package com.example.kafka.consumer;

import com.example.kafka.event.DocumentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentLoggingConsumer {

    @KafkaListener(topics = "document-event", groupId = "document-logging")
    public void consume(DocumentEvent event) {
        System.out.println(
                "[AUDIT] " + event.getType()
                + " | doc =" + event.getDocumentId()
                + " | by=" + event.getActor()
                + " | at=" + event.getOccurredAt()
        );
    }
}
