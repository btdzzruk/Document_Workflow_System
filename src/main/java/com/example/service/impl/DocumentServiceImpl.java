package com.example.service.impl;

import com.example.entity.Document;
import com.example.entity.enums.DocumentStatus;
import com.example.exception.BusinessException;
import com.example.repository.DocumentRepository;
import com.example.service.DocumentService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MinioClient minioClient;

    @Override
    public Document uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new BusinessException("File is empty");
            }

            // Lấy metadata
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String contentType = file.getContentType();
            long size = file.getSize();

            // Tạo object name trong MinIO (unique)
            String objectName = "documents/" + UUID.randomUUID() + "_" + originalFilename;

            // Upload file lên MinIO
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("dung") // buckets trên MinIO
                                .object(objectName)
                                .stream(inputStream, size, -1)
                                .contentType(contentType)
                                .build()
                );
            }

            // Lưu thông tin file vào DB
            Document document = new Document();
            document.setOriginalFilename(originalFilename);
            document.setContentType(contentType);
            document.setSize(size);
            document.setObjectName(objectName);
            document.setStatus(DocumentStatus.UPLOADED);

            return documentRepository.save(document);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Upload file failed: " + e.getMessage());
        }
    }
}
