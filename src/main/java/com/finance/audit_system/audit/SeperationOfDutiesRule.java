package com.finance.audit_system.audit;

import com.finance.audit_system.Entity.EventLog;
import com.finance.audit_system.Entity.Violation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeperationOfDutiesRule implements AuditRule{

    @Override
    public String getRuleId() {
        return "SOD_Rule_01"; // Fancy name for the sepration of duties rule.
    }

    @Override
    public List<Violation> evaluate(List<EventLog> events) {
        List<Violation> violations = new ArrayList<>();

        // Make a hashmap with the with key value pairs being the transaction id and transaction
        // amount respectively.
        Map<String, String> creators = new HashMap<>();

        // 1. First find all the people who made the transactions
        for (EventLog eventLog: events) {
            if ("CREATE_TRANSACTION".equals(eventLog.getAction())) {
                String transactionId = extractTransactionId(eventLog.getMetadata());
                if (transactionId != null) {
                    creators.put(transactionId, eventLog.getActor());
                }
            }
        }

        // 2. Now check all approvers and check them against transaction creators

        for (EventLog eventLog: events) {
            if ("APPROVE_TRANSACTION".equals(eventLog.getAction())) {

                String transactionId = extractTransactionId(eventLog.getMetadata());
                String approver = eventLog.getActor();

                String creator = creators.get(transactionId);

                if (creator != null && creator.equals(approver)) {
                    Violation v = new Violation();
                    v.setRuleId(getRuleId());
                    v.setSeverity("HIGH");
                    v.setDetectedAt(LocalDateTime.now());
                    v.setRelatedEvent(eventLog);
                    v.setDescription("User " + approver + " approved their own transaction. (Transaction ID: " + transactionId + ")");

                    violations.add(v);
                }
            }
        }

        return violations;
    }

    // This is a helper method to extract something like "27" from "Transaction ID: 27, Amount- bla bla bla"
    private String extractTransactionId(String metadata) {
        Pattern pattern = Pattern.compile("Transaction ID: (\\d+)");
        Matcher matcher = pattern.matcher(metadata);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
