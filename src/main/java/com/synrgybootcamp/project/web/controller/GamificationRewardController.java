package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.GamificationPlanetRewardService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.ClaimGamificationRewardResponse;
import com.synrgybootcamp.project.web.model.response.CurrentRewardResponse;
import com.synrgybootcamp.project.web.model.response.DetailRewardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/gamification/rewards")
@Api(tags = "Reward (Gamification)", description = "Reward controller for Gamification")
public class GamificationRewardController {

  @Autowired
  private GamificationPlanetRewardService gamificationPlanetRewardService;

  @GetMapping("")
  @ApiOperation(value = "Current user unclaimed reward")
  public ApiResponse<List<CurrentRewardResponse>> getCurrentReward() {
    List<CurrentRewardResponse> res = gamificationPlanetRewardService.getCurrentReward();

    return new ApiResponse<>("Successfully get current user reward", res);
  }

  @GetMapping("{rewardId}")
  @ApiOperation(value = "get reward detail")
  public ApiResponse<DetailRewardResponse> getRewardDetail(@PathVariable String rewardId) {
    DetailRewardResponse response = gamificationPlanetRewardService.getRewardById(rewardId);

    return new ApiResponse<>("Successfully get planet reward detail", response);
  }

  @PostMapping("{rewardId}/claim")
  @ApiOperation(value = "Claim user rewards")
  public ApiResponse<ClaimGamificationRewardResponse> claimReward(@PathVariable String rewardId) {
    ClaimGamificationRewardResponse response = gamificationPlanetRewardService.claimGamificationReward(rewardId);

    return new ApiResponse<>("Successfully claim planet reward", response);
  }

}
