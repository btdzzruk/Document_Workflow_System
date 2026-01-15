package com.example.service.impl;

import com.example.entity.DocumentComment;
import com.example.kafka.event.CommentEventProducer;
import com.example.kafka.producer.CommentCreatedEvent;
import com.example.repository.CommentRepository;
import com.example.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentEventProducer producer;

    @Override
    public void addComment(Long documentId, String username, String content) {

        DocumentComment comment = new DocumentComment();
        comment.setDocumentId(documentId);
        comment.setUsername(username);
        comment.setContent(content);

        DocumentComment savedComment = commentRepository.save(comment);

        // gửi kafka event
        producer.sendCommentCreated(
                new CommentCreatedEvent(
                        documentId,
                        savedComment.getDocumentId(),
                        username,
                        content
                )
        );
    }

    @Override
    public List<DocumentComment> getCommentsByDocument(Long documentId) {
        return commentRepository
                .findByDocumentIdOrderByCreatedAtAsc(documentId);
    }
}
