package com.example.kafka.consumer;

import com.example.kafka.producer.CommentCreatedEvent;
import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentEmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "document-comment", groupId = "document-comment-group")
    public void handle(CommentCreatedEvent event) {
        // gọi EmailService
        System.out.println(
                "Send email: Document "
                + event.getDocumentId()
                + " có comment mới"
        );
        emailService.sendCommentNotification(event.getDocumentId(), event.getContent());
    }
}
