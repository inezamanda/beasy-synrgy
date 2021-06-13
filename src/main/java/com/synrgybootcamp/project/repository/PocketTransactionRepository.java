package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.PocketTransaction;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PocketTransactionRepository extends JpaRepository<PocketTransaction, String> {
    List<PocketTransaction> findByUserAndSourcePocketOrUserAndDestinationPocket(User user, Pocket source, User user2, Pocket destination);
}
