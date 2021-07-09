package com.synrgybootcamp.project.helper;

import com.synrgybootcamp.project.constant.GamificationConstant;
import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.RewardPlanet;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.entity.UserMission;
import com.synrgybootcamp.project.entity.UserReward;
import com.synrgybootcamp.project.enums.PlanetStatus;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.repository.RewardPlanetRepository;
import com.synrgybootcamp.project.repository.UserMissionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.repository.UserRewardRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.response.UserGamificationStatusResponse;
import com.synrgybootcamp.project.web.model.response.sub.MissionObjResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GamificationHelper {

  private User user;

  public UserMissionRepository userMissionRepository;

  public MissionRepository missionRepository;

  public RewardPlanetRepository rewardPlanetRepository;

  public UserRewardRepository userRewardRepository;

  public UserRepository userRepository;

  public PlanetRepository planetRepository;

  private UserInformation userInformation;

  public GamificationHelper(UserMissionRepository userMissionRepository, MissionRepository missionRepository, RewardPlanetRepository rewardPlanetRepository, UserRewardRepository userRewardRepository, UserRepository userRepository, PlanetRepository planetRepository, UserInformation userInformation) {
    this.userMissionRepository = userMissionRepository;
    this.missionRepository = missionRepository;
    this.rewardPlanetRepository = rewardPlanetRepository;
    this.userRewardRepository = userRewardRepository;
    this.userRepository = userRepository;
    this.planetRepository = planetRepository;
    this.userInformation = userInformation;
  }

  @Bean
  @RequestScope
  public User getUser() {
    return userRepository.findById(userInformation.getUserID())
        .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "user not found"));
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UserGamificationStatusResponse getUserPlanetGamificationStatus() {
    boolean onCompletionDelay = false;
    Planet currentPlanet;

    if (isReadyToStartMission()) {
      currentPlanet = getUser().getPlanet();
    } else {
      Integer planetBefore = getUser().getPlanet().getSequence() - 1;

      currentPlanet = planetRepository.findFirstBySequence(planetBefore).orElse(getUser().getPlanet());
      onCompletionDelay = true;
    }

    return getUserGamificationStatusResponse(currentPlanet, onCompletionDelay);
  }

  public UserGamificationStatusResponse getUserGamificationStatusResponse(Planet planet, boolean onCompletionDelay) {
    Date recentPlanetCompletionDelayFinished = null;
    if(Objects.nonNull(getUser().getLastPlanetCompletionTime())) {
      recentPlanetCompletionDelayFinished = DateUtils.addDays(getUser().getLastPlanetCompletionTime(), 1);
    }

    if (Objects.isNull(planet)) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "You need to start gamification first");
    }

    UserGamificationStatusResponse.UserGamificationStatusResponseBuilder res = UserGamificationStatusResponse.builder()
        .planetId(planet.getId())
        .planetName(planet.getName())
        .planetImage(planet.getImage())
        .planetSequence(planet.getSequence())
        .planetWording(planet.getWording())
        .onCompletionDelay(onCompletionDelay)
        .recentPlanetCompletionDelayFinished(recentPlanetCompletionDelayFinished);

    boolean isOnLastPlanet = planet.getSequence() == GamificationConstant.LAST_PLANET_SEQUENCE;
    boolean isCompletedGamification = false;
    if (isOnLastPlanet) {
      Planet lastPlanet = planetRepository.findFirstBySequence(GamificationConstant.LAST_PLANET_SEQUENCE).orElse(getUser().getPlanet());
      List<Mission> lastPlanetMission = missionRepository.findByPlanet(lastPlanet);
      long completedLastPlanetMission = getTotalCompletedPlanetMission(lastPlanetMission);
      isCompletedGamification = completedLastPlanetMission >= GamificationConstant.MINIMUM_COMPLETED_MISSION;
    } else {
      Planet nextPlanet = planetRepository.findFirstBySequence(planet.getSequence() + 1).orElse(getUser().getPlanet());
      res.nextPlanetId(nextPlanet.getId())
          .nextPlanetName(nextPlanet.getName())
          .nextPlanetSequence(nextPlanet.getSequence())
          .nextPlanetWording(nextPlanet.getWording())
          .nextPlanetImage(nextPlanet.getImage());
    }

    return res.completedGamification(isCompletedGamification)
        .onLastPlanet(isOnLastPlanet)
        .build();
  }

  public void checkForPlanetCompletion() {
    List<Mission> planetMission = missionRepository.findByPlanet(getUser().getPlanet());

    long totalCompleted = getTotalCompletedPlanetMission(planetMission);
    if (totalCompleted >= GamificationConstant.MINIMUM_COMPLETED_MISSION) {
      log.info("user" + getUser().getFullName() + "completed their minimum mission planet");

      addUserReward();
      changeToNextPlanet();
    }
  }

  private long getTotalCompletedPlanetMission(List<Mission> planetMission) {
    return Optional.ofNullable(planetMission)
        .map(res -> res.stream()
            .map(this::fetchOnlyFinishedMission)
            .filter(Objects::nonNull)
            .count())
        .orElse(0L);
  }

  public boolean isReadyToStartMission() {
    log.info("checking user " + getUser().getFullName() + " is ready to start mission or not");

    Date lastCompletion = getUser().getLastPlanetCompletionTime();
    if (Objects.isNull(lastCompletion)) {
      return true;
    }

    Date currentPlanetStartTime = DateUtils.addDays(lastCompletion, 1);

    return currentPlanetStartTime.before(new Date());
  }

  public void startGamificationForNewUser(User user) {
    setUser(user);

    log.info("starting gamification for new user" + getUser().getFullName());

    setUserPlanetBySequence(1, true);
  }

  public PlanetStatus getPlanetStatus(int sequence) {
    int currentUserPlanetSequence = getUser().getPlanet().getSequence();
    if (!isReadyToStartMission()) {
      currentUserPlanetSequence -= 1;
    }

    if (sequence > currentUserPlanetSequence) {
      return PlanetStatus.NEXT;
    } else if (sequence == currentUserPlanetSequence) {
      return PlanetStatus.CURRENT;
    } else {
      return PlanetStatus.DONE;
    }
  }

  public List<MissionObjResponse> getUserMissionStatus(Planet planet) {
    return Optional.ofNullable(planet)
        .map(Planet::getMissions)
        .map(missions -> missions.stream().map(mission -> MissionObjResponse
            .builder()
            .id(mission.getId())
            .missionType(mission.getMissionType())
            .wording(mission.getWording())
            .passed(checkMissionStatus(mission))
            .build()).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  private boolean checkMissionStatus(Mission mission) {
    UserMission userMission = mission.getUserMissions()
        .stream()
        .filter(um -> um.getUser().equals(getUser()))
        .findFirst()
        .orElse(null);

    return Optional.ofNullable(userMission).map(UserMission::getPassed).orElse(false);
  }

  private UserMission fetchOnlyFinishedMission(Mission mission) {

    return mission.getUserMissions()
        .stream()
        .filter(userMission -> userMission.getUser().equals(getUser()) && userMission.getPassed().equals(true))
        .findFirst()
        .orElse(null);
  }

  private void addUserReward() {
    RewardPlanet currentPlanetReward = rewardPlanetRepository.findFirstByPlanet(getUser().getPlanet()).orElse(null);

    UserReward userReward = UserReward
        .builder()
        .rewardPlanet(currentPlanetReward)
        .user(getUser())
        .claimed(false)
        .expired(false)
        .totalUsed(0)
        .build();

    userRewardRepository.save(userReward);

    log.info("added reward " + currentPlanetReward.getWording() + " to user " + getUser().getFullName());
  }

  private void changeToNextPlanet() {
    Integer previousPlanetSequence = getUser().getPlanet().getSequence();
    setUserPlanetBySequence(previousPlanetSequence + 1, false);
  }

  private void setUserPlanetBySequence(Integer sequence, boolean firstPlannet) {
    Planet nextPlalanet = planetRepository.findFirstBySequence(sequence).orElse(null);

    getUser().setPlanet(nextPlalanet);
    getUser().setLastPlanetCompletionTime(firstPlannet ? null : new Date());

    userRepository.save(getUser());
    log.info("changed user " + getUser().getFullName() + " to planet " + nextPlalanet.getName());

    addPlanetMissionToUser(nextPlalanet);
  }

  public void addPlanetMissionToUser(Planet planet) {
    List<UserMission> userMissions = Optional.of(planet)
        .map(Planet::getMissions)
        .map(data -> data.stream()
            .map(this::generateUserMission)
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());

    userMissionRepository.saveAll(userMissions);

    log.info("added mission from planet " + planet.getName() + " to user " + getUser().getFullName());
  }

  private UserMission generateUserMission(Mission mission) {
    return UserMission.builder()
        .mission(mission)
        .user(getUser())
        .passed(false)
        .progress(0)
        .build();
  }
}
