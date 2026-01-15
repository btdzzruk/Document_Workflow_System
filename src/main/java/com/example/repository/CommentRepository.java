package com.example.repository;

import com.example.entity.DocumentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<DocumentComment, Long> {
    List<DocumentComment> findByDocumentIdOrderByCreatedAtAsc(Long documentId);
}

