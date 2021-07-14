package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Account;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByNameIsContainingIgnoreCaseAndUser(String name, User user);
    List<Account> findByAccountNumberContainingIgnoreCaseAndUserAndIdNotIn(String accountNumber, User user, List<String> ids);
    List<Account> findByUser(User user);
    boolean existsByUserAndNameOrUserAndAccountNumber(User user, String name, User userTwo, String accountNumber);
}
