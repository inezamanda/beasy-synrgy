package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.TransactionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByUserAndTypeOrderByDateDesc(User user, TransactionType transactionType);
    List<Transaction> findByUserAndTypeInOrderByDateDesc(User user, List<TransactionType> transactionTypes);
    List<Transaction> findByUserAndDateBetween(User user, Date startTime, Date endTime);
    List<Transaction> findByUser(User user);
    Optional<Transaction> findFirstByUser(User user);
    List<Transaction> findByUserAndType(User user, TransactionType type, Sort sort);
}
