package com.finance.audit_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuditSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditSystemApplication.class, args);
	}

}
