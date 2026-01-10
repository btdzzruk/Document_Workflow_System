package com.example.entity;

import com.example.entity.enums.WorkflowState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "wordflows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkflowState state = WorkflowState.DRAFT;

    @Column(nullable = false)
    private String createdBy;

    private LocalDateTime createAt = LocalDateTime.now();
}
