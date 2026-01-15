package com.example.kafka.consumer;

import com.example.kafka.event.SlaEvent;
import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlaEvetConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "workflow-sla-overdue", groupId = "sla-group")
    public void handleSlaOverdue(SlaEvent event) {

        log.warn(
                "SLA OVERDUE: workflow={}, step={}",
                event.getWorkflowId(),
                event.getStep()
        );

        emailService.sendSlaWarning(
                event.getWorkflowId(),
                event.getStep(),
                event.getDeadline()
        );
    }
}
