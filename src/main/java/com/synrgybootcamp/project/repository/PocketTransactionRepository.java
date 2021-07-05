package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.PocketTransaction;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PocketTransactionRepository extends JpaRepository<PocketTransaction, String> {
    List<PocketTransaction> findByUserIdAndSourcePocketIdOrUserIdAndDestinationPocketId(String userId, String sourcePocketId, String userIdTwo, String destionationPocketId, Sort sort);
}
