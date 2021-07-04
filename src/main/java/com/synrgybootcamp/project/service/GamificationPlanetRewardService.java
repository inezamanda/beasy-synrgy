package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.ClaimGamificationRewardResponse;
import com.synrgybootcamp.project.web.model.response.CurrentRewardResponse;
import com.synrgybootcamp.project.web.model.response.DetailRewardResponse;

import java.util.List;

public interface GamificationPlanetRewardService {
    DetailRewardResponse getPlanetRewardById(String planetId);
    DetailRewardResponse getRewardById(String rewardId);
    ClaimGamificationRewardResponse claimGamificationReward(String rewardId);
    List<CurrentRewardResponse> getCurrentReward();
}
