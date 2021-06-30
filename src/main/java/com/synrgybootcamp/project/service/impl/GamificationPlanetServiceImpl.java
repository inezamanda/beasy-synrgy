package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.enums.PlanetStatus;
import com.synrgybootcamp.project.helper.GamificationHelper;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.service.GamificationPlanetService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.response.ListPlanetResponse;
import com.synrgybootcamp.project.web.model.response.PlanetDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GamificationPlanetServiceImpl implements GamificationPlanetService {

  @Autowired
  private PlanetRepository planetRepository;

  @Autowired
  private GamificationHelper gamificationHelper;

  @Override
  public List<ListPlanetResponse> getAllPlanets(PlanetStatus status) {
    List<Planet> planets = planetRepository.findAllByOrderBySequenceAsc();

    Stream<ListPlanetResponse> mappedPlanetData = planets.stream()
        .map(planet -> ListPlanetResponse
            .builder()
            .id(planet.getId())
            .name(planet.getName())
            .image(planet.getImage())
            .sequence(planet.getSequence())
            .wording(planet.getWording())
            .rewardId(planet.getRewardPlanet().getId())
            .status(gamificationHelper.getPlanetStatus(planet.getSequence()))
            .build());

    if (Objects.nonNull(status)) {
      mappedPlanetData.filter(planet -> planet.getStatus().equals(status));
    }

    return mappedPlanetData.collect(Collectors.toList());
  }

  @Override
  public PlanetDetailResponse getPlanetDetail(String planetId) {
    Planet planet = planetRepository.findById(planetId)
        .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Planet tidak ditemukan"));

    return PlanetDetailResponse.builder()
        .id(planet.getId())
        .name(planet.getName())
        .sequence(planet.getSequence())
        .image(planet.getImage())
        .storytelling(planet.getStorytelling())
        .wording(planet.getWording())
        .rewardId(planet.getRewardPlanet().getId())
        .status(gamificationHelper.getPlanetStatus(planet.getSequence()))
        .mission(gamificationHelper.getUserMissionStatus(planet))
        .build();
  }
}
