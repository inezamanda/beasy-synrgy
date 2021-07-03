package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.GamificationPlanetRewardService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.ClaimGamificationRewardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/gamification/rewards")
@Api(tags = "Reward (Gamification)", description = "Reward controller for Gamification")
public class GamificationRewardController {

  @Autowired
  private GamificationPlanetRewardService gamificationPlanetRewardService;

  @PostMapping("/{rewardId}/claim")
  @ApiOperation(value = "Claim user rewards")
  public ApiResponse<ClaimGamificationRewardResponse> claimReward(@PathVariable String rewardId) {
    ClaimGamificationRewardResponse response = gamificationPlanetRewardService.claimGamificationReward(rewardId);

    return new ApiResponse<>("Successfully claim planet reward", response);
  }

}
