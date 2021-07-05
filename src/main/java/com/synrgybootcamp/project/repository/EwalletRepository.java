package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Ewallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EwalletRepository extends JpaRepository<Ewallet, String> {
}
