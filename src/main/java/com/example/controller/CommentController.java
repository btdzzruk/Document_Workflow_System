package com.example.controller;

import com.example.dto.request.CreateCommentRequest;
import com.example.dto.response.APIResponse;
import com.example.service.CommentService;
import com.example.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/documents/{id}")
    public APIResponse<Void> addComment(@PathVariable Long id,
                                        @RequestBody @Valid
                                        CreateCommentRequest request) {

        String username = SecurityUtils.getCurrentUsername();
        commentService.addComment(id, username, request.getContent());

        APIResponse<Void> response = new APIResponse<>();
        response.setSuccess(true);
        response.setMessage("Thêm comment thành công !");
        response.setData(null);
        return response;
    }
}
