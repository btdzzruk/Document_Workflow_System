package com.example.entity;

import com.example.entity.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;
    private String contentType;
    private long size;

    private String objectName; // MinIO path gốc
    private String previewObject; // MinIO preview path

    @Enumerated(EnumType.STRING)
    private DocumentStatus status; // UPLOADED, PREVIEW_READY

    private String createBy;

    private LocalDateTime createAt =  LocalDateTime.now();
}
