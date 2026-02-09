package com.finance.audit_system.audit;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.Violation;

import java.util.List;

public interface AuditRule {

    String getRuleId();

    List<Violation> evaluate(List<EventLog> events); // Provided a list of events, this will find the violation
}
