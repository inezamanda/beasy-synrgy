package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.entity.RewardPlanet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardPlanetRepository extends JpaRepository<RewardPlanet, String> {
  Optional<RewardPlanet> findFirstByPlanet(Planet planet);
}
