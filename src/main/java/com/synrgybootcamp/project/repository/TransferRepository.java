package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Transfer;
import com.synrgybootcamp.project.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, String> {
    List<Transfer> findByUser(User user, Sort sort);
}
