package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Contact;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findByNameIsContainingIgnoreCaseAndUserAndDeleteIsFalse(String name, User user);
    List<Contact> findByAccountNumberContainingIgnoreCaseAndUserAndIdNotInAndDeleteIsFalse(String accountNumber, User user, List<String> ids);
    List<Contact> findByUserAndDeleteFalse(User user);
    boolean existsByUserAndNameAndDeleteIsFalseOrUserAndAccountNumberAndDeleteIsFalse(User user, String name, User userTwo, String accountNumber);
    boolean existsByUserAndNameAndIdNotAndDeleteIsFalseOrUserAndAccountNumberAndIdNotAndDeleteIsFalse(User user, String name, String id, User userTwo, String accountNumber, String idTwo);
}
