package com.example.service.impl;

import com.example.dto.request.DocumentSearchRequest;
import com.example.entity.Document;
import com.example.entity.enums.DocumentStatus;
import com.example.exception.NotFoundException;
import com.example.kafka.event.PreviewGenerateEvent;
import com.example.repository.DocumentRepository;
import com.example.repository.spec.DocumentSpecification;
import com.example.service.DocumentService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MinioClient minioClient;
    private final KafkaTemplate<String, PreviewGenerateEvent> kafkaTemplate;

    private static final String PREVIEW_TOPIC = "document_preview";

    @Override
    @Transactional
    public Document uploadFile(Long documentId, MultipartFile file) {

        // lấy document
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new NotFoundException("Không tìm thấy tài liệu với ID: " + documentId)
                );

        try {
            // upload file lên MinIO
            String objectName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    io.minio.PutObjectArgs.builder()
                            .bucket("documents")
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // cập nhật document
            document.setObjectName(objectName);
            document.setStatus(DocumentStatus.UPLOADED);
            Document saved = documentRepository.save(document);

            // gửi Kafka event tạo preview
            kafkaTemplate.send(
                    PREVIEW_TOPIC,
                    new PreviewGenerateEvent(saved.getId(), objectName)
            );

            return saved;

        } catch (Exception e) {
            document.setStatus(DocumentStatus.FAILED);
            documentRepository.save(document);
            throw new RuntimeException("Upload file thất bại", e);
        }
    }

    @Override
    public Document findById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Không tìm thấy tài liệu với ID: " + id)
                );
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> search(DocumentSearchRequest request) {
        return documentRepository.findAll(
                DocumentSpecification.search(request)
        );
    }

    // Document theo trạng thái
    @Override
    public Map<String, Long> countByStatus() {
        Map<String, Long> result = new HashMap<>();

        for (Object[] row : documentRepository.countByStatus()) {
            DocumentStatus status = (DocumentStatus) row[0];
            Long count = (Long) row[1];
            result.put(status.name(), count);
        }
        return result;
    }
}
