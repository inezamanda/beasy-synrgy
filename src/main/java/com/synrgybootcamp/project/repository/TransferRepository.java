package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, String> {
}
