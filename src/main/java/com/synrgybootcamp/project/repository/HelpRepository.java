package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Help;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRepository extends JpaRepository<Help, String> {
}
