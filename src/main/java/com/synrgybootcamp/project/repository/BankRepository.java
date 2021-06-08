package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, String> {
}
