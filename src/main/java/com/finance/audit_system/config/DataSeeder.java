package com.finance.audit_system.config;

import com.finance.audit_system.Entity.Role;
import com.finance.audit_system.Entity.User;
import com.finance.audit_system.Entity.UserStatus;
import com.finance.audit_system.dao.UserRepository;
import com.finance.audit_system.service.FinanceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FinanceService financeService;

    public DataSeeder(UserRepository userRepository, FinanceService financeService) {
        this.userRepository = userRepository;
        this.financeService = financeService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("SEEDING DATA ...");

        // 1. Create a normal user
        User userGeneric = new User();
        userGeneric.setUsername("generic_user");
        userGeneric.setRole(Role.ACCOUNTANT);
        userGeneric.setStatus(UserStatus.ACTIVE);
        userRepository.save(userGeneric);

        // 2. Create an admin user
        User userAdmin = new User();
        userAdmin.setUsername("admin_user");
        userAdmin.setRole(Role.ADMIN);
        userAdmin.setStatus(UserStatus.ACTIVE);
        userRepository.save(userAdmin);

        // 3. user creates transaction
        System.out.println("GENERIC USER CREATING TRANSACTION ...");
        financeService.createTransaction(userGeneric.getId(), new BigDecimal("100.0"), "PAYMENT");

        // 4. admin approves transaction
        System.out.println("ADMIN USER CREATING TRANSACTION ...");
        financeService.approveTransaction(1L, userAdmin.getId()); // transacction id is 1 because this is the first transaction being made

        System.out.println("DATA SEEDING COMPLETE");
    }
}
