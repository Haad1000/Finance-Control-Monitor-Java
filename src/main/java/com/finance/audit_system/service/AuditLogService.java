package com.finance.audit_system.service;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.dao.EventLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    private final EventLogRepository eventLogRepository;

    public AuditLogService(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    public void logEvent(String actor, String action, String metadata) {
        EventLog log = new EventLog();
        log.setActor(actor);
        log.setAction(action);
        log.setMetadata(metadata);
        log.setTimestamp(LocalDateTime.now());

        eventLogRepository.save(log);
        System.out.println("AUDIT LOG: [ " + action + " ] by " + actor);
    }
}
