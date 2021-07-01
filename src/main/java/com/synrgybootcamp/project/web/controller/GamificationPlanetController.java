package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.enums.PlanetStatus;
import com.synrgybootcamp.project.service.GamificationPlanetService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.ListPlanetResponse;
import com.synrgybootcamp.project.web.model.response.PlanetDetailResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/gamification/planets")
@Api(tags = "Planet (Gamification)", description = "Planet Controller for Gamification")
public class GamificationPlanetController {

  @Autowired
  private GamificationPlanetService gamificationPlanetService;

  @GetMapping("")
  @ApiOperation(value = "Get list of planets")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> getPlanets(
      @RequestParam(required = false) PlanetStatus status
  ) {
    List<ListPlanetResponse> planetResponseList = gamificationPlanetService.getAllPlanets(status);

    return new ResponseEntity<>(
        new ApiResponse("success get planet lists", planetResponseList), HttpStatus.OK
    );
  }

  @GetMapping("/{planetId}")
  @ApiOperation(value = "Get detail of planet")
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse> getPlanetDetail(
      @PathVariable String planetId
  ) {
    PlanetDetailResponse planet = gamificationPlanetService.getPlanetDetail(planetId);

    return new ResponseEntity<>(
        new ApiResponse("success get planet detail", planet), HttpStatus.OK
    );
  }

}
