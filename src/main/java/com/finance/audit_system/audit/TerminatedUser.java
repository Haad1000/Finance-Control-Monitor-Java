package com.finance.audit_system.audit;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.User;
import com.finance.audit_system.Entity.UserStatus;
import com.finance.audit_system.Entity.Violation;
import com.finance.audit_system.dao.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TerminatedUser implements AuditRule{

    private final UserRepository userRepository;

    public TerminatedUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getRuleId() {
        return "TD_Rule_02"; // Similar to SeperationOfDuties, this ia a fancy name for this rule.
    }

    @Override
    public List<Violation> evaluate(List<EventLog> events) {

        List<Violation> violations = new ArrayList<>();

        for (EventLog eventLog: events) {
            String username = eventLog.getActor();

            // Now that we have the user, now we look him up
            Optional<User> userOptional = userRepository.findUserByUsername(username);

            if (userOptional.isPresent() && userOptional.get().getStatus() == UserStatus.TERMINATED) {

                Violation v = new Violation();
                v.setRuleId(getRuleId());
                v.setSeverity("CRITICAL");
                v.setDetectedAt(LocalDateTime.now());
                v.setRelatedEvent(eventLog);
                v.setDescription("Terminated user: " + username + " performed an action: " + eventLog.getAction());

                violations.add(v);
            }
        }

        return violations;
    }
}
