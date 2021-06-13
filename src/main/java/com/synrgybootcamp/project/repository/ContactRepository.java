package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Contact;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, String> {
    List<Contact> findByNameIsContainingIgnoreCaseAndUser(String name, User user);
    List<Contact> findByAccountNumberContainingIgnoreCaseAndUserAndIdNotIn(String accountNumber, User user, List<String> ids);
    List<Contact> findByUser(User user);
}
