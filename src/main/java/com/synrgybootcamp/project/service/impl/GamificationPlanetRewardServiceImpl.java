package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.RewardPlanet;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.entity.UserReward;
import com.synrgybootcamp.project.enums.RewardPlanetType;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.RewardPlanetRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.repository.UserRewardRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.GamificationPlanetRewardService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.VoucherDiscountUtil;
import com.synrgybootcamp.project.web.model.response.ClaimGamificationRewardResponse;
import com.synrgybootcamp.project.web.model.response.CurrentRewardResponse;
import com.synrgybootcamp.project.web.model.response.DetailRewardResponse;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GamificationPlanetRewardServiceImpl implements GamificationPlanetRewardService {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private RewardPlanetRepository rewardPlanetRepository;

    @Autowired
    private UserRewardRepository userRewardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    UserInformation userInformation;

    @Override
    public DetailRewardResponse getPlanetRewardById(@PathVariable String planetId) {

        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user tidak ditemukan"));

        boolean claimed = Optional.ofNullable(planet)
            .map(Planet::getRewardPlanet)
            .map(RewardPlanet::getUserRewards)
            .flatMap(ur -> ur.stream().filter(r -> r.getUser().equals(user)).findFirst())
            .map(UserReward::getClaimed)
            .orElse(false);

        return DetailRewardResponse.builder()
                .id(planet.getRewardPlanet().getId())
                .type(planet.getRewardPlanet().getType())
                .wording(planet.getRewardPlanet().getWording())
                .tnc(planet.getRewardPlanet().getTnc())
                .claimed(claimed)
                .build();
    }

    @Override
    public ClaimGamificationRewardResponse claimGamificationReward(String rewardId) {
        UserReward userReward = userRewardRepository.findByUserIdAndRewardId(userInformation.getUserID(), rewardId)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user tidak memiliki reward tersebut"));
        userReward.setClaimed(true);
        userReward.setExpiredAt(DateUtils.addMonths(new Date(), 1));

        RewardPlanetType type = userReward.getRewardPlanet().getType();

        ClaimGamificationRewardResponse.ClaimGamificationRewardResponseBuilder response = ClaimGamificationRewardResponse
            .builder()
            .id(userReward.getRewardId())
            .type(type)
            .wording(userReward.getRewardPlanet().getWording());

        if (type.name().equals("DISCOUNT")) {
            String generatedVoucher = VoucherDiscountUtil.generateVoucher();
            response.additionalInformation(generatedVoucher);
        } else if (type.name().equals("CASHBACK")) {
            Pocket pocket = pocketRepository.findPrimaryPocket(userReward.getUserId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Primary pocket not found"));
            pocket.setBalance(pocket.getBalance() + userReward.getRewardPlanet().getAmount());
            pocketRepository.save(pocket);
        }

        userRewardRepository.save(userReward);

        return response.build();
    }

    @Override
    public List<CurrentRewardResponse> getCurrentReward() {
        return userRewardRepository.findByUserIdAndClaimedFalse(userInformation.getUserID())
            .stream()
            .map(UserReward::getRewardPlanet)
            .map(reward -> CurrentRewardResponse.builder()
                .id(reward.getId())
                .type(reward.getType())
                .wording(reward.getWording())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public DetailRewardResponse getRewardById(String rewardId) {
        RewardPlanet reward = rewardPlanetRepository.findById(rewardId)
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "reward tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user tidak ditemukan"));

        boolean claimed = Optional.ofNullable(reward)
            .map(RewardPlanet::getUserRewards)
            .flatMap(ur -> ur.stream().filter(r -> r.getUser().equals(user)).findFirst())
            .map(UserReward::getClaimed)
            .orElse(false);

        return DetailRewardResponse.builder()
            .id(reward.getId())
            .type(reward.getType())
            .wording(reward.getWording())
            .tnc(reward.getTnc())
            .claimed(claimed)
            .build();
    }
}
