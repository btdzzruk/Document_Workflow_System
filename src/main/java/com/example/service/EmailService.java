package com.example.service;

import java.time.LocalDateTime;

public interface EmailService {
    void sendCommentNotification(Long documentId, String content);

    void sendSlaWarning(Long workflowId, String step, LocalDateTime deadline);
}
