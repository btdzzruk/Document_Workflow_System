package com.example.service;

import com.example.entity.enums.WorkflowState;

import java.util.Map;

public interface WorkflowService {

    Long createWorkflow(Long documentId,String createBy);

    void submit(Long wordflowId);

    WorkflowState getCurrentStat(Long workflowId);

    Map<String, Long> countByReviewer();

    long avgApprovalTimeHours();

    void review(Long workflowId);

    void approve(Long workflowId);

    void reject(Long workflowId);
}
