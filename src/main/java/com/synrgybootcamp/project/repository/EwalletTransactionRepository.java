package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.EwalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EwalletTransactionRepository extends JpaRepository<EwalletTransaction, String> {
}
