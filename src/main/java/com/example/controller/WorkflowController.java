package com.example.controller;

import com.example.dto.response.APIResponse;
import com.example.entity.Workflow;
import com.example.entity.enums.WorkflowState;
import com.example.service.WorkflowService;
import com.example.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/documents/{docId}/workflow")
    public APIResponse<Long> createWorkflow(@PathVariable Long docId) {

        String username = SecurityUtils.getCurrentUsername();
        Long workflowId = workflowService.createWorkflow(docId, username);

        APIResponse<Long> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Tạo workflow thành công");
        response.setData(workflowId);
        return response;
    }

    // submit workflow
    // DRAFT -> SUBMITTED
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/submit")
    public APIResponse<Void> submit(@PathVariable Long id) {
        workflowService.submit(id);

        APIResponse<Void> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Submit workflow thành công !");
        response.setData(null);
        return response;
    }

    // lấy trạng thái hiện tại: redis -> sql fallback
    @PreAuthorize("hasAnyRole('USER','REVIEWER','APPROVER','ADMIN')")
    @GetMapping("/{id}/state")
    public APIResponse<WorkflowState> getWorkflowState(@PathVariable Long id) {
        WorkflowState state = workflowService.getCurrentStat(id);

        APIResponse<WorkflowState> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Lấy trạng thái workflow thành công !");
        response.setData(state);
        return response;
    }
}
