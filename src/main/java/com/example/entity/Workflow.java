package com.example.entity;

import com.example.entity.enums.WorkflowState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflows")
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

    @Column(name = "document_id", nullable = false)
    private Long documentId;

    @Column(nullable = false)
    private String createdBy; // user name từ keycloak

    private String reviewer; // người review (admin)

    private LocalDateTime reviewAt; // lúc review
    private LocalDateTime approvedAt; // lúc approved

    // sla fields
    private LocalDateTime reviewDeadline;
    private LocalDateTime approvedDeadline;

    private LocalDateTime createAt = LocalDateTime.now();
}
