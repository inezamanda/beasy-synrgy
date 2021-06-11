package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
