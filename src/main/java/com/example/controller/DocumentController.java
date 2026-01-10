package com.example.controller;

import com.example.dto.response.APIResponse;
import com.example.entity.Document;
import com.example.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse<Document>> uploadFile(@RequestParam("file") MultipartFile file) {
        Document document = documentService.uploadFile(file);

        APIResponse<Document> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("File successfully uploaded");
        response.setData(document);
        return ResponseEntity.ok(response);
    }
}
