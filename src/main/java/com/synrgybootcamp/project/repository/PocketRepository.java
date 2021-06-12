package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PocketRepository extends JpaRepository<Pocket, String> {
    @Query(value = "SELECT * FROM pockets p WHERE p.user_id = :userId AND is_primary = true"
            , nativeQuery = true)
    Optional<Pocket> findPrimaryPocket(@Param("userId") String userId);
    @Query(value = "SELECT * FROM pockets p WHERE p.user_id = :userId AND is_primary = false"
            , nativeQuery = true)
    List<Pocket> findListPocket(@Param("userId") String userId);
}
