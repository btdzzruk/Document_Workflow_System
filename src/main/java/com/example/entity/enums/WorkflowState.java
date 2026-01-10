package com.example.entity.enums;

public enum WorkflowState {

    DRAFT,
    SUBMITTED,
    REVIEWED,
    APPROVED,
    REJECTED;

    // Kiểm tra chuyển trạng thái hợp lệ
    public boolean canTransitionTo(WorkflowState next) {
        return switch (this) {
            case DRAFT -> next == SUBMITTED;
            case SUBMITTED -> next == REVIEWED || next == REJECTED;
            case REVIEWED -> next == APPROVED || next == REJECTED;
            case APPROVED, REJECTED -> false;
        };
    }
}
