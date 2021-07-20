package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRewardRepository extends JpaRepository<UserReward, String> {
  List<UserReward> findByUserIdAndClaimedFalse(String userId);
  Optional<UserReward> findByUserIdAndRewardId(String userId, String rewardId);
  List<UserReward> findByUserIdAndClaimedTrueAndExpiredAtAfter(String userId, Date maximumExpiredAt);

}
