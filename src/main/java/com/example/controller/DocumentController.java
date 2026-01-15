package com.example.controller;

import com.example.dto.request.DocumentSearchRequest;
import com.example.dto.response.APIResponse;
import com.example.entity.Document;
import com.example.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/upload")
    public ResponseEntity<APIResponse<Document>> uploadFile(
            @PathVariable("id") Long documentId,
            @RequestParam("file") MultipartFile file
    ) {
        Document document = documentService.uploadFile(documentId, file);

        APIResponse<Document> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Tệp đã được tải lên thành công!");
        response.setData(document);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public APIResponse<List<Document>> search(@RequestBody DocumentSearchRequest request){
        APIResponse<List<Document>> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Tìm kiếm tài liệu thành công !");
        response.setData(documentService.search(request));
        return response;
    }
}
