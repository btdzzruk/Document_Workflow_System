package com.example.controller;

import com.example.dto.response.APIResponse;
import com.example.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
public class WorkflowReviewController {

    private final WorkflowService workflowService;

    @PreAuthorize("hasAnyRole('REVIEWER', 'ADMIN')")
    @PostMapping("/{id}/review")
    public APIResponse<Void> review(@PathVariable Long id){
        workflowService.review(id);

        APIResponse<Void> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Review workflow thành công !");
        response.setData(null);
        return response;
    }

    @PreAuthorize("hasAnyRole('APPROVER', 'ADMIN')")
    @PostMapping("/{id}/approve")
    public APIResponse<Void> approve(@PathVariable Long id){
        workflowService.approve(id);

        APIResponse<Void> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Approve workflow thành công !");
        response.setData(null);
        return response;
    }

    @PreAuthorize("hasAnyRole('REVIEWER', 'APPROVER', 'ADMIN')")
    @PostMapping("/{id}/reject")
    public APIResponse<Void> reject(@PathVariable Long id){
        workflowService.reject(id);

        APIResponse<Void> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Reject workflow thành công !");
        response.setData(null);
        return response;
    }
}
