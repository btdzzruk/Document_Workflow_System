package com.example.service;

public interface NotificationService {

    void notifyDocumentUploaded(Long documentId);

    void notifyDocumentReviewed(Long documentId);

    void notifyDocumentApproved(Long documentId);
}
