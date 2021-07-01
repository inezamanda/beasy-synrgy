package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.entity.UserMission;
import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.repository.UserMissionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.GamificationUserService;
import com.synrgybootcamp.project.util.ApiException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class GamificationUserServiceImpl implements GamificationUserService {

  @Autowired
  private GamificationHelper gamificationHelper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMissionRepository userMissionRepository;

  @Autowired
  private MissionRepository missionRepository;

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private UserInformation userInformation;

  @Override
  public void startGamification() {
    User user = userRepository.findById(userInformation.getUserID())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

    if (Objects.nonNull(user.getPlanet())) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "User already started gamification");
    }

    gamificationHelper.startGamificationForNewUser(user);
  }

  @Override
  public void finishMission(String missionId) {
    User user = userRepository.findById(userInformation.getUserID())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

    Mission mission = missionRepository.findById(missionId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mission not found"));

    UserMission userMission = userMissionRepository.findByMissionAndUser(mission, user)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User mission not found"));

    userMission.setPassed(true);
    userMissionRepository.save(userMission);

    gamificationHelper.checkForPlanetCompletion();
  }

  @Override
  public void movePlanet(String planetId) {
    User user = userRepository.findById(userInformation.getUserID())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

    Planet planet = planetRepository.findById(planetId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Planet data not found"));

    user.setPlanet(planet);
    user.setLastPlanetCompletionTime(new Date());
    userRepository.save(user);

    gamificationHelper.addPlanetMissionToUser(planet);
  }

  @Override
  public void skipPlanetDelay() {
    User user = userRepository.findById(userInformation.getUserID())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

    user.setLastPlanetCompletionTime(null);
  }
}
