package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRewardRepository extends JpaRepository<UserReward, String> {
}
