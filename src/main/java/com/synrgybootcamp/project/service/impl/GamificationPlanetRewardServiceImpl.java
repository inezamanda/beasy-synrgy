package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.Pocket;
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
import com.synrgybootcamp.project.web.model.response.GamificationRewardPlanetResponse;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Service
public class GamificationPlanetRewardServiceImpl implements GamificationPlanetRewardService {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private UserRewardRepository userRewardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    UserInformation userInformation;

    @Override
    public GamificationRewardPlanetResponse getPlanetRewardById(@PathVariable String planetId) {

        Planet planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user tidak ditemukan"));

        boolean claimed = false;
        if(planet.getRewardPlanet().getUserRewards() != null){
            List<UserReward> userRewards = planet.getRewardPlanet().getUserRewards();
            for (UserReward userReward : userRewards){
                if(userReward.getUser().equals(user)){
                    claimed = userReward.getClaimed();
                }
            }
        }

        return GamificationRewardPlanetResponse.builder()
                .id(planet.getId())
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
}
