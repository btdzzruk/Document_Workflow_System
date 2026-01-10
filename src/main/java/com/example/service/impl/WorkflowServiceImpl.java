package com.example.service.impl;

import com.example.entity.Workflow;
import com.example.entity.enums.WorkflowState;
import com.example.exception.BusinessException;
import com.example.exception.NotFoundException;
import com.example.repository.WorkflowRepository;
import com.example.service.WorkflowCacheService;
import com.example.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowCacheService workflowCacheService;

    @Override
    public Long createWorkflow(Long createBy) {
        Workflow workflow = new Workflow();
        workflow.setState(WorkflowState.DRAFT);
        workflow.setCreatedBy(String.valueOf(createBy));

        Workflow saved = workflowRepository.save(workflow);

        // cache trạng thái ban đầu
        workflowCacheService.cacheState(
                saved.getId(),
                saved.getState().name()
        );
        return saved.getId();
    }

    @Override
    public void submit(Long wordflowId) {
        Workflow workflow = workflowRepository.findById(wordflowId)
                .orElseThrow(() -> new NotFoundException("Workflow không tồn tại !"));

        // validate state transition
        if (!workflow.getState().canTransitionTo(WorkflowState.SUBMITTED)) {
            throw new BusinessException("Không thể SUBMIT khi trạng thái hiện tại là: "
                    + workflow.getState());
        }
        workflow.setState(WorkflowState.SUBMITTED);
        workflowRepository.save(workflow);

        // cache redis
        workflowCacheService.cacheState(
                wordflowId,
                workflow.getState().name()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public WorkflowState getCurrentStat(Long workflowId) {

        // redis
        String cachedState = workflowCacheService.getState(workflowId);
        if (cachedState != null) {
            return WorkflowState.valueOf(cachedState);
        }

        // sql
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new NotFoundException("Workflow không tồn tại !"));

        workflowCacheService.cacheState(
                workflowId,
                workflow.getState().name()
        );
        return workflow.getState();
    }
}
