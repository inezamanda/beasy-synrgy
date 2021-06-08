package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.PocketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketTransactionRepository extends JpaRepository<PocketTransaction, String> {
}
