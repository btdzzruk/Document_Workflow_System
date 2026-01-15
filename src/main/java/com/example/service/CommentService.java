package com.example.service;

import com.example.entity.DocumentComment;

import java.util.List;

public interface CommentService {
    void  addComment(Long documentId, String username, String content);

    List<DocumentComment> getCommentsByDocument(Long documentId);
}
