package com.example.service.impl;

import com.example.service.WorkflowCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WorkflowCacheServiceImpl implements WorkflowCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String STATE_KEY = "wordflow:state:";
    private static final long TTL_MINUTES = 30;

    @Override
    public void cacheState(Long workflowId, String state) {
        redisTemplate.opsForValue().set(STATE_KEY + workflowId, state,
                Duration.ofMinutes(TTL_MINUTES));
    }

    @Override
    public String getState(Long workflowId) {
        Object value = redisTemplate.opsForValue().get(STATE_KEY + workflowId);
        return value != null ? value.toString() : null;
    }

    @Override
    public void evictState(Long workflowId) {
        redisTemplate.delete(STATE_KEY + workflowId);
    }
}
