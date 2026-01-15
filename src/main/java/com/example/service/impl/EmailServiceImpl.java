package com.example.service.impl;

import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendCommentNotification(Long documentId, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hantho1107@gmail.com");
        message.setSubject("Document " + documentId + " có comment mới");
        message.setText("Nội dung comment:\n\n" + content);
        mailSender.send(message);
    }

    @Override
    public void sendSlaWarning(Long workflowId, String step, LocalDateTime deadline) {
        String content = """
                Workflow %d đã quá hạn bước %s
                Deadline: %s
                """.formatted(workflowId, step, deadline);

        // mock gửi mail / slack / notification
        log.warn("SLA MAIL:\n{}", content);
    }
}