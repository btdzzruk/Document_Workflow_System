package com.example.kafka.consumer;

import com.example.entity.Document;
import com.example.entity.enums.DocumentStatus;
import com.example.kafka.event.PreviewGenerateEvent;
import com.example.repository.DocumentRepository;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreviewConsumer {

    private final MinioClient minioClient;
    private final DocumentRepository documentRepository;

    @KafkaListener(topics = "document-preview", groupId = "preview-group")
    public void handle(PreviewGenerateEvent event) {
        // Mock generate preview
        String previewObject = "preview/" + event.getDocumentId() +" .pdf";

        // upload preview lên MinIO
        Document document = documentRepository.findById(event.getDocumentId()).orElseThrow();
        document.setPreviewObject(previewObject);
        document.setStatus(DocumentStatus.PREVIEW_READY);

        documentRepository.save(document);
    }
}
