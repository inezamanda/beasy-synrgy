package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Account;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByNameIsContainingIgnoreCaseAndUserAndDeleteIsFalse(String name, User user);
    List<Account> findByAccountNumberContainingIgnoreCaseAndUserAndIdNotInAndDeleteIsFalse(String accountNumber, User user, List<String> ids);
    List<Account> findByUserAndDeleteIsFalse(User user);
    boolean existsByUserAndNameAndDeleteIsFalseOrUserAndAccountNumberAndDeleteIsFalse(User user, String name, User userTwo, String accountNumber);
    boolean existsByUserAndNameAndIdNotAndDeleteIsFalseOrUserAndAccountNumberAndIdNotAndDeleteIsFalse(User user, String name, String id, User userTwo, String accountNumber, String idTwo);
}
