package com.example.service;

import com.example.entity.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    Document uploadFile(MultipartFile file);
}