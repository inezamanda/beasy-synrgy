package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission,String> {
}
