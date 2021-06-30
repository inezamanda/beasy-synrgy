package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserMissionRepository extends JpaRepository<UserMission, String> {
  Optional<UserMission> findByMissionAndUser(Mission mission, User user);
}
