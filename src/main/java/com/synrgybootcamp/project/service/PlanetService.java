package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;

import java.util.List;

public interface PlanetService {
    List<PlanetResponse> getAllPlanet();
    PlanetResponse createPlanet(PlanetRequest planetRequest);
    PlanetResponse getPlanetById(String id);
    PlanetResponse updatePlanetById(String id, PlanetRequest planetRequest);
    boolean deletePlanetById(String id);
}
