package com.synrgybootcamp.project.service;

public interface GamificationUserService {
  void startGamification();
  void finishMission(String missionId);
  void movePlanet(String planetId);
  void skipPlanetDelay();
}
