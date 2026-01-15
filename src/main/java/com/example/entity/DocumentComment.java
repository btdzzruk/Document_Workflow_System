package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "document_comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private Long commentId;
    private String username;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt =  LocalDateTime.now();
}
