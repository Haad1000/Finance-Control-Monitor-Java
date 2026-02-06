package com.finance.audit_system.dao;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

    Optional<User> findUserByUserName(String username);
}
