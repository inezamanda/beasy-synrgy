package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PlanetService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    private PlanetRepository planetRepository;


    @Override
    public List<PlanetResponse> getAllPlanet() {

        List<Planet> planets = planetRepository.findAll();

        return planets.stream().map(p -> PlanetResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .story_telling(p.getStory_telling())
                .sequence(p.getSequence()).build()).collect(Collectors.toList());
    }

    @Override
    public PlanetResponse createPlanet(PlanetRequest planetRequest) {

        Planet planet = planetRepository.save(
                Planet.builder()
                        .name(planetRequest.getName())
                        .story_telling(planetRequest.getStory_telling())
                        .sequence(planetRequest.getSequence())
                        .build()
        );

        return PlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .story_telling(planet.getStory_telling())
                .sequence(planet.getSequence())
                .build();

    }

    @Override
    public PlanetResponse getPlanetById(String id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet yang dipilih tidak ditemukan"));


        return PlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .story_telling(planet.getStory_telling())
                .sequence(planet.getSequence())
                .build();
    }

    @Override
    public PlanetResponse updatePlanetById(String id, PlanetRequest planetRequest) {
        Planet planet = planetRepository.findById(id).orElseThrow(()->
                new ApiException(HttpStatus.NOT_FOUND,"Planet tidak ditemukan"));

        planet.setName(planetRequest.getName());
        planet.setStory_telling(planetRequest.getStory_telling());
        planet.setSequence(planetRequest.getSequence());

        Planet planetResult = planetRepository.save(planet);

        return PlanetResponse.builder()
                .id(planetResult.getId())
                .name(planetResult.getName())
                .story_telling(planetResult.getStory_telling())
                .sequence(planetResult.getSequence())
                .build();
    }

    @Override
    public boolean deletePlanetById(String id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet yang dipilih tidak ditemukan"));

        planetRepository.delete(planet);

        return true;
    }
}
