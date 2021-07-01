package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.PlanetRewardService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.PlanetRewardRequest;
import com.synrgybootcamp.project.web.model.response.PlanetRewardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Api(tags = "Reward", description = "Rewards Controller")
public class PlanetRewardController {

  @Autowired
  PlanetRewardService planetRewardService;

  @GetMapping("")
  @ApiOperation(value = "Get list of planet's rewards")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> listPlanetRewards() {
    List<PlanetRewardResponse> planetRewardResponses = planetRewardService.getAllPlanetRewards();

    return new ResponseEntity<>(
        new ApiResponse("success get all planet rewards", planetRewardResponses), HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get detail of planet's reward")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> getPlanetRewardById(
      @PathVariable String id
  ){

    PlanetRewardResponse planetRewardResponse = planetRewardService.getPlanetRewardById(id);

    return new ResponseEntity<>(
        new ApiResponse("success get detail planet reward", planetRewardResponse), HttpStatus.OK
    );
  }

  @PostMapping("")
  @ApiOperation(value = "Add new planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> createPlanetReward(@RequestBody PlanetRewardRequest payload){

    PlanetRewardResponse planetRewardResponse = planetRewardService.createPlanetReward(payload);

    return new ResponseEntity<>(
        new ApiResponse("success add new planet reward", planetRewardResponse), HttpStatus.CREATED
    );
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Edit planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> updatePlanetRewardById(
      @PathVariable String id,
      @RequestBody PlanetRewardRequest payload
  ){
    PlanetRewardResponse planetRewardResponse  = planetRewardService.updatePlanetRewardById(id, payload);

    return new ResponseEntity<>(
        new ApiResponse("success edit planet reward", planetRewardResponse), HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete planet's reward (Admin Only)")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> deletePlanetRewardById(@PathVariable String id){
    planetRewardService.deletePlanetRewardById(id);

    return new ResponseEntity<>(
        new ApiResponse("success delete planet reward"), HttpStatus.OK
    );
  }

}
