package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.repository.MissionRepository;
import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PlanetService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.UploadFileUtil;
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

    @Autowired
    UploadFileUtil uploadFileUtil;

    @Override
    public List<PlanetResponse> getAllPlanet() {

        List<Planet> planets = planetRepository.findAll();

        return planets.stream()
            .map(planet -> PlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .image(planet.getImage())
                .storyTelling(planet.getStorytelling())
                .sequence(planet.getSequence())
                .wording(planet.getWording())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PlanetResponse createPlanet(PlanetRequest planetRequest) {

        String planetImage = uploadFileUtil.upload(planetRequest.getImage());

        Planet planet = planetRepository.save(
                Planet.builder()
                    .name(planetRequest.getName())
                    .image(planetImage)
                    .storytelling(planetRequest.getStoryTelling())
                    .sequence(planetRequest.getSequence())
                    .wording(planetRequest.getWording())
                    .build()
        );

        System.out.println(planetRequest);

        return PlanetResponse.builder()
            .id(planet.getId())
            .name(planet.getName())
            .image(planet.getImage())
            .storyTelling(planet.getStorytelling())
            .sequence(planet.getSequence())
            .wording(planet.getWording())
            .build();
    }

    @Override
    public PlanetResponse getPlanetById(String id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "planet yang dipilih tidak ditemukan"));


        return PlanetResponse.builder()
            .id(planet.getId())
            .name(planet.getName())
            .image(planet.getImage())
            .storyTelling(planet.getStorytelling())
            .sequence(planet.getSequence())
            .wording(planet.getWording())
            .build();
    }

    @Override
    public PlanetResponse updatePlanetById(String id, PlanetRequest planetRequest) {
        Planet planet = planetRepository.findById(id).orElseThrow(()->
                new ApiException(HttpStatus.NOT_FOUND,"Planet tidak ditemukan"));

        String planetImage = uploadFileUtil.upload(planetRequest.getImage());

        planet.setName(planetRequest.getName());
        planet.setStorytelling(planetRequest.getStoryTelling());
        planet.setSequence(planetRequest.getSequence());
        planet.setImage(planetImage);
        planet.setWording(planetRequest.getWording());

        Planet planetResult = planetRepository.save(planet);

        return PlanetResponse.builder()
            .id(planetResult.getId())
            .name(planetResult.getName())
            .image(planetResult.getImage())
            .storyTelling(planetResult.getStorytelling())
            .sequence(planetResult.getSequence())
            .wording(planetResult.getWording())
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
