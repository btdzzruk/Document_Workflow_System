package com.example.kafka.event;

import com.example.entity.enums.DocumentEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentEvent {

    private DocumentEventType type;
    private Long documentId;
    private String actor; // ai thực hiện
    private LocalDateTime occurredAt;

}
