package com.example.sla;

import com.example.entity.Workflow;
import com.example.kafka.event.SlaEvent;
import com.example.repository.WorkflowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SlaCheckerJob {

    private final WorkflowRepository workflowRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedRate = 60000) // mỗi 1 phút
    public void checkDeadline() {
        LocalDateTime now = LocalDateTime.now();

        // quá hạn review
        List<Workflow> overdueReview = workflowRepository.findOverdueReview(now);
        overdueReview.forEach(workflow -> {
            kafkaTemplate.send("workflow-sla-overdue", new SlaEvent(workflow.getDocumentId(),
                    "REVIEW", workflow.getReviewDeadline()));
        });

        // quá hạn approve
        List<Workflow> overdueApprove = workflowRepository.findOverdueApprove(now);
        overdueApprove.forEach(workflow -> {
            kafkaTemplate.send("workflow-sla-overdue", new SlaEvent(workflow.getDocumentId(),
                    "APPROVE", workflow.getApprovedDeadline()));
        });
    }
}
