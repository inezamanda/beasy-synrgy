package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.GamificationUserService;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "User Gamification", description = "User Controller for Gamification")
public class GamificationUserController {

  @Autowired
  GamificationUserService gamificationUserService;

  @PostMapping("api/gamification/start")
  @ApiOperation(value = "start gamification for user who doesnt started gamification yet")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponseWithoutData startGamification() {
    gamificationUserService.startGamification();

    return new ApiResponseWithoutData("success start gamification for user");
  }

  @PostMapping("api/gamification/cheat/mission/{missionId}")
  @ApiOperation(value = "auto complete mission for user [DEVELOPMENT PURPOSE ONLY]")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponseWithoutData autoFinishMission(@PathVariable String missionId) {
    gamificationUserService.finishMission(missionId);

    return new ApiResponseWithoutData("success finish mission for user");
  }

  @PostMapping("api/gamification/cheat/planet/move/{planetId}")
  @ApiOperation(value = "auto move user to other planet [DOESNT ADD REWARD FROM SKIPPED PLANET]")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponseWithoutData autoMovePlanet(@PathVariable String planetId) {
    gamificationUserService.movePlanet(planetId);

    return new ApiResponseWithoutData("success move user to to other planet");
  }

  @PostMapping("api/gamification/cheat/planet/skip-delay")
  @ApiOperation(value = "skip 24 hours delay to start next planet")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ApiResponseWithoutData skipPlanetDelay() {
    gamificationUserService.skipPlanetDelay();

    return new ApiResponseWithoutData("success skip user planet delay");
  }
}
