package com.example.service.impl;

import com.example.service.EmailService;
import com.example.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;

    @Override
    public void notifyDocumentUploaded(Long documentId) {
        // hiện tại chưa gửi mail
        System.out.println("Notify upload: doc " + documentId);
    }

    @Override
    public void notifyDocumentReviewed(Long documentId) {
        System.out.println("Notify review: doc " + documentId);
    }

    @Override
    public void notifyDocumentApproved(Long documentId) {
        System.out.println("Notify approved: doc " + documentId);
    }
}
