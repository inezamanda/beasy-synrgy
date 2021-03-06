package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String username);
    Optional<User> findByVerifCode(String verifCode);
    boolean existsByEmail(String email);
    boolean existsByAccountNumber(String accountNumber);
    Optional<User> findByAccountNumber(String accountNumber);
}
