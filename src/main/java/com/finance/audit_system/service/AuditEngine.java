package com.finance.audit_system.service;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.Violation;
import com.finance.audit_system.audit.AuditRule;
import com.finance.audit_system.dao.EventLogRepository;
import com.finance.audit_system.dao.ViolationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditEngine {

    private final EventLogRepository eventLogRepository;
    private final ViolationRepository violationRepository;
    private final List<AuditRule> auditRules;

    public AuditEngine(EventLogRepository eventLogRepository, ViolationRepository violationRepository, List<AuditRule> auditRules) {
        this.eventLogRepository = eventLogRepository;
        this.violationRepository = violationRepository;
        this.auditRules = auditRules;
    }

    @Scheduled(fixedDelay = 10000)
    public void runAudit() {
        System.out.println("Audit Engine: Scanning for rule violations");

        List<EventLog> eventLogs = eventLogRepository.findAll();

        for (AuditRule rules: auditRules) {

            List<Violation> violations = rules.evaluate(eventLogs);

            for (Violation v : violations) {
                boolean alreadyExists = violationRepository.existsByRelatedEvent(v.getRelatedEvent());

                if (!alreadyExists) {
                    System.out.println("NEW VIOLATION FOUND: " + rules.getRuleId());
                    violationRepository.save(v);
                }
            }
        }
    }
}
