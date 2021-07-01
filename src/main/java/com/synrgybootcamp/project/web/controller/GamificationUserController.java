package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.GamificationUserService;
import com.synrgybootcamp.project.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<ApiResponse> startGamification() {

    gamificationUserService.startGamification();

    return new ResponseEntity<>(
        new ApiResponse("success start gamification for user"), HttpStatus.OK
    );
  }

  @PostMapping("api/gamification/cheat/mission/{missionId}")
  @ApiOperation(value = "auto complete mission for user [DEVELOPMENT PURPOSE ONLY]")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> autoFinishMission(@PathVariable String missionId) {

    gamificationUserService.finishMission(missionId);

    return new ResponseEntity<>(
        new ApiResponse("success finish mission for user"), HttpStatus.OK
    );
  }

  @PostMapping("api/gamification/cheat/planet/move/{planetId}")
  @ApiOperation(value = "auto move user to other planet [DOESNT ADD REWARD FROM SKIPPED PLANET]")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> autoMovePlanet(@PathVariable String planetId) {

    gamificationUserService.movePlanet(planetId);

    return new ResponseEntity<>(
        new ApiResponse("success move user to to other planet"), HttpStatus.OK
    );
  }

  @PostMapping("api/gamification/cheat/planet/skip-delay")
  @ApiOperation(value = "skip 24 hours delay to start next planet")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> skipPlanetDelay() {

    gamificationUserService.skipPlanetDelay();

    return new ResponseEntity<>(
        new ApiResponse("success skip user planet delay"), HttpStatus.OK
    );
  }
}
