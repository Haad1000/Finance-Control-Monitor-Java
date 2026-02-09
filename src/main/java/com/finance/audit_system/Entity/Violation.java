package com.finance.audit_system.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "violations")
@Data
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ruleId;

    @Column(nullable = false)
    private String severity; // something like "LOW" or "HIGH" etc.

    @Column(nullable = false)
    private String description; // basically like the metadata of the Violation

    @Column(nullable = false)
    private LocalDateTime detectedAt;

    @OneToOne
    @JoinColumn(name = "event_log_id")
    private EventLog relatedEvent;
}
