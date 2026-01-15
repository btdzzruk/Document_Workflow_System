package com.example.service;

import com.example.dto.request.DocumentSearchRequest;
import com.example.entity.Document;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DocumentService {
    Document uploadFile(Long documentId, MultipartFile file);

    // Lấy document theo id
    Document findById(Long id);

    // Danh sách document của user (dùng cho UI)
    List<Document> findAll();

    List<Document> search(DocumentSearchRequest request);

    Map<String, Long> countByStatus();
}