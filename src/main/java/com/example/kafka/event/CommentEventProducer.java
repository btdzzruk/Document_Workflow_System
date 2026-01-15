package com.example.kafka.event;

import com.example.kafka.producer.CommentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCommentCreated(CommentCreatedEvent event){
        kafkaTemplate.send("document-comment", event);
    }
}
