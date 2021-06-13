package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.PocketTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PocketTransactionRepository extends JpaRepository<PocketTransaction, String> {
    @Query(value = "SELECT * FROM pocket_transactions p WHERE p.user_id = :userId"
            , nativeQuery = true)
    List<PocketTransaction> findHistoryPocket(@Param("userId") String userId);

}
