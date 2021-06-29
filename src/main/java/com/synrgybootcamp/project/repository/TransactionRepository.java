package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserAndTypeOrderByDateDesc(User user, TransactionType transactionType);
    List<Transaction> findByUserAndTypeIn(User user, List<TransactionType> transactionTypes);
}
