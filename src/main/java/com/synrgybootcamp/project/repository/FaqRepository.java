package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Faq;
import com.synrgybootcamp.project.entity.Help;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, String> {
}
