package com.example.controller;

import com.example.dto.response.APIResponse;
import com.example.service.DocumentService;
import com.example.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DocumentService documentService;
    private final WorkflowService workflowService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/documents-by-status")
    public APIResponse<?> documentsByStatus(){
        APIResponse<Object> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Văn bản theo trạng thái thành công !");
        response.setData(documentService.countByStatus());
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reviewer-workload")
    public APIResponse<Map<String, Long>> reviewerWorkload(){

        Map<String, Long> data = workflowService.countByReviewer();

        APIResponse<Map<String, Long>> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Số lượng xử lý theo reviewer thành công !");
        response.setData(data);
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/avg-approval-time")
    public APIResponse<?> avgApprovalTime(){
        APIResponse<Object> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Thời gian phê duyệt trung bình thành công !");
        response.setData(workflowService.avgApprovalTimeHours());
        return response;
    }
}
