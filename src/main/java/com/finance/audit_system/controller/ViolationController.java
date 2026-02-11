package com.finance.audit_system.controller;

import com.finance.audit_system.Entity.Violation;
import com.finance.audit_system.dao.ViolationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/violations")
public class ViolationController {

    private final ViolationRepository violationRepository;

    public ViolationController(ViolationRepository violationRepository) {
        this.violationRepository = violationRepository;
    }

    // it's on localhost:8080/api/violations I know I'm lazy so i can copy paste from here lol
    @GetMapping
    public List<Violation> getAllViolation() {
        return violationRepository.findAll();
    }
}
