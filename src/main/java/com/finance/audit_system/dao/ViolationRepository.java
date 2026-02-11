package com.finance.audit_system.dao;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Long> {

    boolean existsByRelatedEvent(EventLog eventLog);
}
