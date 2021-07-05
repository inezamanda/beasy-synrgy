package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.PlanetRewardRequest;
import com.synrgybootcamp.project.web.model.response.PlanetRewardResponse;

import java.util.List;

public interface PlanetRewardService {
  List<PlanetRewardResponse> getAllPlanetRewards();
  PlanetRewardResponse createPlanetReward(PlanetRewardRequest planetRewardRequest);
  PlanetRewardResponse getPlanetRewardById(String id);
  PlanetRewardResponse updatePlanetRewardById(String id, PlanetRewardRequest planetRewardRequest);
  boolean deletePlanetRewardById(String id);
}
