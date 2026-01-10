package com.example.service;

import com.example.entity.enums.WorkflowState;

public interface WorkflowService {

    Long createWorkflow(Long createBy);

    void submit(Long wordflowId);

    WorkflowState getCurrentStat(Long workflowId);
}
