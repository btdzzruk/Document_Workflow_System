package com.example.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentCreatedEvent {
    private Long documentId;
    private Long commentId;
    private String username;
    private String content;
}
