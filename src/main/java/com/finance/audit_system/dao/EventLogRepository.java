package com.finance.audit_system.dao;

import com.finance.audit_system.Entity.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
