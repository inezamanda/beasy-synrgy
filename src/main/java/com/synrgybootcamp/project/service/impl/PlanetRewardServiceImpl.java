package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.RewardPlanet;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.repository.RewardPlanetRepository;
import com.synrgybootcamp.project.service.PlanetRewardService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.PlanetRewardRequest;
import com.synrgybootcamp.project.web.model.response.PlanetRewardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetRewardServiceImpl implements PlanetRewardService {

  @Autowired
  PlanetRepository planetRepository;

  @Autowired
  RewardPlanetRepository rewardPlanetRepository;

  @Override
  public List<PlanetRewardResponse> getAllPlanetRewards() {
    List<RewardPlanet> rewardPlanets = rewardPlanetRepository.findAll();

    return rewardPlanets.stream().map(this::toWebResponse).collect(Collectors.toList());
  }

  @Override
  public PlanetRewardResponse createPlanetReward(PlanetRewardRequest planetRewardRequest) {
    Planet planet = planetRepository.findById(planetRewardRequest.getPlanetId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet yang dipilih tidak ditemukan"));

    RewardPlanet result = rewardPlanetRepository.save(
        RewardPlanet.builder()
            .type(planetRewardRequest.getType())
            .tnc(planetRewardRequest.getTnc())
            .amount(planetRewardRequest.getAmount())
            .wording(planetRewardRequest.getWording())
            .planet(planet)
            .build()
    );

    return toWebResponse(result);
  }

  @Override
  public PlanetRewardResponse getPlanetRewardById(String id) {
    RewardPlanet rewardPlanet = rewardPlanetRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "reward planet yang dipilih tidak ditemukan"));

    return toWebResponse(rewardPlanet);
  }

  @Override
  public PlanetRewardResponse updatePlanetRewardById(String id, PlanetRewardRequest planetRewardRequest) {
    Planet planet = planetRepository.findById(planetRewardRequest.getPlanetId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet yang dipilih tidak ditemukan"));

    RewardPlanet rewardPlanet = rewardPlanetRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "reward planet yang dipilih tidak dapat ditemukan"));


    rewardPlanet.setPlanet(planet);
    rewardPlanet.setType(planetRewardRequest.getType());
    rewardPlanet.setAmount(planetRewardRequest.getAmount());
    rewardPlanet.setTnc(planetRewardRequest.getTnc());
    rewardPlanet.setWording(planetRewardRequest.getWording());
    RewardPlanet result = rewardPlanetRepository.save(rewardPlanet);

    return toWebResponse(result);
  }

  @Override
  public boolean deletePlanetRewardById(String id) {
    RewardPlanet rewardPlanet = rewardPlanetRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "reward planet yang dipilih tidak dapat ditemukan"));

    rewardPlanetRepository.delete(rewardPlanet);

    return true;
  }

  private PlanetRewardResponse toWebResponse(RewardPlanet rewardPlanet) {
    return PlanetRewardResponse.builder()
        .id(rewardPlanet.getId())
        .amount(rewardPlanet.getAmount())
        .planetId(rewardPlanet.getPlanet().getId())
        .planetName(rewardPlanet.getPlanet().getName())
        .type(rewardPlanet.getType())
        .tnc(rewardPlanet.getTnc())
        .wording(rewardPlanet.getWording())
        .build();
  }
}
