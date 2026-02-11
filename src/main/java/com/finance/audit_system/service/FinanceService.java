package com.finance.audit_system.service;

import com.finance.audit_system.Entity.Transaction;
import com.finance.audit_system.Entity.User;
import com.finance.audit_system.dao.EventLogRepository;
import com.finance.audit_system.dao.TransactionRepository;
import com.finance.audit_system.dao.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FinanceService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final EventLogRepository eventLogRepository;
    private final AuditLogService auditLogService;

    public FinanceService(TransactionRepository transactionRepository, UserRepository userRepository, EventLogRepository eventLogRepository, AuditLogService auditLogService) {
        this.eventLogRepository = eventLogRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public Transaction createTransaction(Long id, BigDecimal amount, String type) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setStatus("PENDING");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setCreatedBy(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        auditLogService.logEvent(
                user.getUsername(),
                "CREATE_TRANSACTION",
                "Transaction ID: " + savedTransaction.getId() + ", Amount: " + amount + ", Type: " + type
        );

        return savedTransaction;
    }

    @Transactional
    public Transaction approveTransaction(Long transactionId, Long approverId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));

        User approver = userRepository.findById(approverId).orElseThrow(() -> new RuntimeException("User(Approver) not found"));

        transaction.setStatus("APPROVED");
        transaction.setApprovedBy(approver);

        transactionRepository.save(transaction);

        auditLogService.logEvent(
                approver.getUsername(),
                "APPROVE_TRANSACTION",
                "Transaction ID: " + transactionId + " approved"
        );

        return transaction;
    }
}
