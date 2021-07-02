package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.ClaimGamificationRewardResponse;
import com.synrgybootcamp.project.web.model.response.GamificationRewardPlanetResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface GamificationPlanetRewardService {
    GamificationRewardPlanetResponse getPlanetRewardById(String planetId);
    ClaimGamificationRewardResponse claimGamificationReward(String rewardId);
}
