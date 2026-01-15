package com.example.service.impl;

import com.example.entity.Workflow;
import com.example.entity.enums.WorkflowState;
import com.example.exception.BusinessException;
import com.example.exception.NotFoundException;
import com.example.repository.WorkflowRepository;
import com.example.service.WorkflowCacheService;
import com.example.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdBy;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final WorkflowCacheService workflowCacheService;

    @Override
    public Long createWorkflow(Long documentId, String createdBy) {

        Workflow workflow = new Workflow();

        // gắn workflow với document
        workflow.setDocumentId(documentId);

        // trạng thái ban đầu
        workflow.setState(WorkflowState.DRAFT);

        // user tạo workflow (username từ Keycloak)
        workflow.setCreatedBy(createdBy);

        // thời điểm tạo
        workflow.setCreateAt(LocalDateTime.now());

        // lưu DB
        Workflow saved = workflowRepository.save(workflow);

        // cache trạng thái ban đầu vào Redis (KEY = workflowId)
        workflowCacheService.cacheState(
                saved.getDocumentId(),
                saved.getState().name()
        );

        return saved.getDocumentId();
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

        // sla: reviewer có 24h
        // khi submit set reviewDeadline
        workflow.setReviewDeadline(LocalDateTime.now().plusHours(24));

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

    // Số lượng xử lý theo reviewer
    @Override
    public Map<String, Long> countByReviewer() {

        Map<String, Long> result = new HashMap<>();

        for (Object[] row : workflowRepository.countByReviewer()) {
            String reviewer = (String) row[0];
            Long count = (Long) row[1];
            result.put(reviewer, count);
        }

        return result;
    }

    // Thời gian phê duyệt trung bình
    @Override
    public long avgApprovalTimeHours() {
        Long seconds = workflowRepository.avgApprovalTimeSeconds();
        return seconds == null ? 0 : seconds / 3600;
    }

    // khi review -> set approveDeadline
    @Override
    public void review(Long workflowId) {
        Workflow wf = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new BusinessException("Workflow not found"));

        // Lấy reviewer hiện tại (Keycloak)
        String reviewer = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        wf.setReviewer(reviewer);
        wf.setReviewAt(LocalDateTime.now());
        wf.setState(WorkflowState.REVIEWED);

        // SLA: approve trong 48h
        wf.setApprovedDeadline(LocalDateTime.now().plusHours(48));

        workflowRepository.save(wf);
    }

    @Override
    public void approve(Long workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new NotFoundException("Workflow not found"));

        if (!workflow.getState().canTransitionTo(WorkflowState.APPROVED)) {
            throw new BusinessException("Không thể approve workflow hiện tại");
        }

        workflow.setState(WorkflowState.APPROVED);
        workflow.setApprovedAt(LocalDateTime.now());

        workflowRepository.save(workflow);
    }

    @Override
    public void reject(Long workflowId) {
        Workflow workflow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new NotFoundException("Workflow not found"));

        if (!workflow.getState().canTransitionTo(WorkflowState.REJECTED)) {
            throw new BusinessException("Không thể reject workflow hiện tại");
        }

        workflow.setState(WorkflowState.REJECTED);

        workflowRepository.save(workflow);
    }
}
