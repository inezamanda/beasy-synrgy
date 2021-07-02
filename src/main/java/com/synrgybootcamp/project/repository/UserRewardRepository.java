package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRewardRepository extends JpaRepository<UserReward, String> {
  Optional<UserReward> findByUserIdAndRewardId(String userId, String rewardId);
  List<UserReward> findByUserIdAndClaimedTrueAndExpiredAtBefore(String userId, Date maximumExpiredAt);
}
