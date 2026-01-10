package com.example.service;

public interface WorkflowCacheService {

    void cacheState(Long workflowId, String state);

    String getState(Long workflowId);

    void evictState(Long workflowId);
}
