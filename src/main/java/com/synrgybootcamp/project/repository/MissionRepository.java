package com.synrgybootcamp.project.repository;

import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.enums.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, String> {
  Optional<Mission> findByMissionTypeAndPlanet(MissionType missionType, Planet planet);
  List<Mission> findByPlanet(Planet planet);
}
