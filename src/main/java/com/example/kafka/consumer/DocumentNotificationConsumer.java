package com.example.kafka.consumer;

import com.example.kafka.event.DocumentEvent;
import com.example.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentNotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "document-event", groupId = "document-notification")
    public void consume(DocumentEvent event) {
        switch (event.getType()) {
            case DOC_UPLOADED ->
                notificationService.notifyDocumentUploaded(event.getDocumentId());

            case DOC_REVIEWED ->
                notificationService.notifyDocumentReviewed(event.getDocumentId());

            case DOC_APPROVED ->
                notificationService.notifyDocumentApproved(event.getDocumentId());
        }
    }
}
