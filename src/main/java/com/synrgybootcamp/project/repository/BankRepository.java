package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Bank;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, String> {
    @Override
    List<Bank> findAll(Sort sort);
}
