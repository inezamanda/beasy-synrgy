package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.enums.PlanetStatus;
import com.synrgybootcamp.project.web.model.response.ListPlanetResponse;
import com.synrgybootcamp.project.web.model.response.PlanetDetailResponse;

import java.util.List;

public interface GamificationPlanetService {
  List<ListPlanetResponse> getAllPlanets(PlanetStatus status);
  PlanetDetailResponse getPlanetDetail(String planetId);
}
