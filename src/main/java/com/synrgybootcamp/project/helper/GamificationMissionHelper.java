package com.synrgybootcamp.project.helper;

import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.UserMission;
import com.synrgybootcamp.project.enums.MissionType;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.repository.RewardPlanetRepository;
import com.synrgybootcamp.project.repository.UserMissionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.repository.UserRewardRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class GamificationMissionHelper extends GamificationHelper {

  public GamificationMissionHelper(UserMissionRepository userMissionRepository, MissionRepository missionRepository, RewardPlanetRepository rewardPlanetRepository, UserRewardRepository userRewardRepository, UserRepository userRepository, PlanetRepository planetRepository, UserInformation userInformation) {
    super(userMissionRepository, missionRepository, rewardPlanetRepository, userRewardRepository, userRepository, planetRepository, userInformation);
  }

  public void checkAndValidateTransferMission() {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.TRANSFER);
    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("transfer mission on user" + getUser().getFullName() + "found, validating progress");

          Integer currentProgress = res.getProgress();
          res.setProgress(currentProgress + 1);

          log.info("transfer mission progress for user " + getUser().getFullName() + " added to " + res.getProgress());

          if (res.getProgress().equals(userMission.getMission().getTarget())) {
            res.setPassed(true);
            log.info("transfer mission passed on user" + getUser().getFullName());
          }

          super.userMissionRepository.save(res);
        });

    checkForPlanetCompletion();
  }

  public void checkAndValidatePocketCreationMission(Pocket pocket) {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.POCKET_CREATION);

    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("pocket creation mission on user " + getUser().getFullName() + " found, validating progress");

          int missionTarget = res.getMission().getTarget();
          if (pocket.getTarget() >= missionTarget) {
            res.setPassed(true);
            log.info("pocket creation mission passed on user " + getUser().getFullName());

            super.userMissionRepository.save(res);
          }
        });

    checkForPlanetCompletion();
  }

  public void checkAndValidatePocketTopUpMission(Integer topUpAmount) {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.POCKET_TOPUP);

    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("pocket topup mission on user " + getUser().getFullName() + " found, validating progress");

          int missionTarget = res.getMission().getTarget();
          if (topUpAmount >= missionTarget) {
            res.setPassed(true);
            super.userMissionRepository.save(res);

            log.info("pocket topup mission passed on user " + getUser().getFullName());
          }
        });

    checkForPlanetCompletion();
  }

  public void checkAndValidateMerchantPaymentMission(Integer paymentAmount) {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.PAYMENT_MERCHANT);

    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("merchant payment mission on user " + getUser().getFullName() + " found, validating progress");

          int missionTarget = res.getMission().getTarget();
          if (paymentAmount >= missionTarget) {
            res.setPassed(true);
            super.userMissionRepository.save(res);

            log.info("merchant payment mission passed on user " + getUser().getFullName());
          }
        });

    checkForPlanetCompletion();
  }

  public void checkAndValidatePaymentMobileMission(Integer paymentAmount) {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.PAYMENT_MOBILE);

    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("mobile payment mission on user " + getUser().getFullName() + " found, validating progress");

          int missionTarget = res.getMission().getTarget();
          if (paymentAmount >= missionTarget) {
            res.setPassed(true);
            super.userMissionRepository.save(res);

            log.info("mobile payment mission passed on user " + getUser().getFullName());
          }
        });

    checkForPlanetCompletion();
  }

  public void checkAndValidateCreditCardMission(Integer paymentAmount) {
    if (!super.isReadyToStartMission()) return;

    UserMission userMission = checkIsUserHaveMission(MissionType.PAYMENT_CREDIT_CARD);

    Optional.ofNullable(userMission)
        .ifPresent(res -> {
          log.info("credit card mission on user " + getUser().getFullName() + " found, validating progress");

          int missionTarget = res.getMission().getTarget();
          if (paymentAmount >= missionTarget) {
            res.setPassed(true);
            super.userMissionRepository.save(res);

            log.info("credit card mission passed on user " + getUser().getFullName());
          }
        });

    checkForPlanetCompletion();
  }

  private UserMission checkIsUserHaveMission(MissionType missionType) {
    Mission mission = super.missionRepository
        .findByMissionTypeAndPlanet(missionType, super.getUser().getPlanet()).orElse(null);

    return Optional.ofNullable(mission)
        .map(var -> userMissionRepository
            .findByMissionAndUser(var, super.getUser())
            .filter(userMission -> !userMission.getPassed())
            .orElse(null))
        .orElse(null);
  }
}
