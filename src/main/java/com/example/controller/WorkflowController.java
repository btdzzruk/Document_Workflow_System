package com.example.controller;

import com.example.dto.response.APIResponse;
import com.example.entity.Workflow;
import com.example.entity.enums.WorkflowState;
import com.example.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    public APIResponse<Long> createWorkflow() {

        Long workflowId = workflowService.createWorkflow(1L); // giả lập user

        APIResponse<Long> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Tạo workflow thành công");
        response.setData(workflowId);
        return response;
    }

    // submit workflow
    // DRAFT -> SUBMITTED
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
    @GetMapping("/{id}state")
    public APIResponse<WorkflowState> getWorkflowState(@PathVariable Long id) {
        WorkflowState state = workflowService.getCurrentStat(id);

        APIResponse<WorkflowState> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Lấy trạng thái workflow thành công !");
        response.setData(state);
        return response;
    }
}
