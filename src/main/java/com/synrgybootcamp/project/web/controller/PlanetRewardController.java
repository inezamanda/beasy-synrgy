package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.PlanetRewardService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.web.model.request.PlanetRewardRequest;
import com.synrgybootcamp.project.web.model.response.PlanetRewardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rewards")
@Api(tags = "Reward", description = "Rewards Controller [ADMIN]")
public class PlanetRewardController {

  @Autowired
  PlanetRewardService planetRewardService;

  @GetMapping("")
  @ApiOperation(value = "Get list of planet's rewards")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponse<List<PlanetRewardResponse>> listPlanetRewards() {
    List<PlanetRewardResponse> planetRewardResponses = planetRewardService.getAllPlanetRewards();

    return new ApiResponse<>("success get all planet rewards", planetRewardResponses);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get detail of planet's reward")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponse<PlanetRewardResponse> getPlanetRewardById(
      @PathVariable String id
  ){

    PlanetRewardResponse planetRewardResponse = planetRewardService.getPlanetRewardById(id);

    return new ApiResponse<>("success get detail planet reward", planetRewardResponse);
  }

  @PostMapping("")
  @ApiOperation(value = "Add new planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ApiResponse<PlanetRewardResponse> createPlanetReward(@RequestBody PlanetRewardRequest payload){
    PlanetRewardResponse planetRewardResponse = planetRewardService.createPlanetReward(payload);

    return new ApiResponse<>("success add new planet reward", planetRewardResponse);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Edit planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ApiResponse<PlanetRewardResponse> updatePlanetRewardById(
      @PathVariable String id,
      @RequestBody PlanetRewardRequest payload
  ){
    PlanetRewardResponse planetRewardResponse  = planetRewardService.updatePlanetRewardById(id, payload);

    return new ApiResponse<>("success edit planet reward", planetRewardResponse);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ApiResponseWithoutData deletePlanetRewardById(@PathVariable String id){
    planetRewardService.deletePlanetRewardById(id);

    return new ApiResponseWithoutData("success delete planet reward");
  }

}
