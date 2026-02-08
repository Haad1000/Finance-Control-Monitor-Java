package com.finance.audit_system.service;

import com.finance.audit_system.Entity.User;
import com.finance.audit_system.dao.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    // Will add "Create User" logic here later for our Seeder
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
