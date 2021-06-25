package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Bank;
import com.synrgybootcamp.project.entity.Mission;
import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.service.MissionService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.MissionRequest;
import com.synrgybootcamp.project.web.model.response.MissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Override
    public List<MissionResponse> getAllMission() {
        List<Mission> missions = missionRepository.findAll();

        return missions.stream().map(m -> MissionResponse.builder()
                .id(m.getId())
                .missionType(m.getMissionType())
                .target(m.getTarget())
                .wording(m.getWording())
                .planetId(m.getPlanet().getId()).build()).collect(Collectors.toList());
    }

    @Override
    public MissionResponse createMission(MissionRequest missionRequest) {

        Planet planet = planetRepository.findById(missionRequest.getPlanetId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Planet tidak ditemukan"));

        Mission mission = missionRepository.save(
                Mission.builder()
                        .missionType(missionRequest.getMissionType())
                        .target(missionRequest.getTarget())
                        .wording(missionRequest.getWording())
                        .planet(planet)
                        .build()
        );

        return MissionResponse.builder()
                .id(mission.getId())
                .missionType(mission.getMissionType())
                .target(mission.getTarget())
                .wording(mission.getWording())
                .planetId(mission.getPlanet().getId())
                .build();
    }

    @Override
    public MissionResponse getMissionById(String id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Misi tidak ditemukan"));


        return MissionResponse.builder()
                .id(mission.getId())
                .missionType(mission.getMissionType())
                .target(mission.getTarget())
                .wording(mission.getWording())
                .planetId(mission.getPlanet().getId())
                .build();
    }

    @Override
    public MissionResponse updateMissionById(String id, MissionRequest missionRequest) {

        Mission mission = missionRepository.findById(id).orElseThrow(()->
                new ApiException(HttpStatus.NOT_FOUND,"Misi tidak ditemukan"));

        Planet planet = planetRepository.findById(missionRequest.getPlanetId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Planet tidak ditemukan"));

        mission.setMissionType(missionRequest.getMissionType());
        mission.setTarget(missionRequest.getTarget());
        mission.setWording(missionRequest.getWording());
        mission.setPlanet(planet);

        Mission missionResult = missionRepository.save(mission);

        return MissionResponse.builder()
                .id(missionResult.getId())
                .missionType(missionResult.getMissionType())
                .target(missionResult.getTarget())
                .wording(missionResult.getWording())
                .planetId(missionResult.getPlanet().getId())
                .build();
    }

    @Override
    public boolean deleteMissionById(String id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Misi tidak ditemukan"));

        missionRepository.delete(mission);

        return true;
    }
}
