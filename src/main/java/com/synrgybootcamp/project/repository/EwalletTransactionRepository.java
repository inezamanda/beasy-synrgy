package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.EwalletTransaction;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EwalletTransactionRepository extends JpaRepository<EwalletTransaction, String> {
    List<EwalletTransaction> findByUser(User user, Sort sort);
}
