package com.synrgybootcamp.project.web.controller;


import com.synrgybootcamp.project.repository.PlanetRepository;
import com.synrgybootcamp.project.service.impl.PlanetServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.ContactResponse;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/planet")
public class PlanetController {

    @Autowired
    private PlanetServiceImpl planetService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> listPlanet() {
        List<PlanetResponse> planetResponses = planetService.getAllPlanet();
        return new ResponseEntity<>(
                new ApiResponse("success get all planets", planetResponses), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPlanetById(
            @PathVariable String id){
        PlanetResponse planetResponse = planetService.getPlanetById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail planet", planetResponse), HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addPlanet(@RequestBody PlanetRequest planetRequest){
        PlanetResponse planetResponse = planetService.createPlanet(planetRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new planet", planetResponse), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePlanetById(
            @PathVariable String id,
            @RequestBody PlanetRequest planetRequest){
        PlanetResponse planetResponse = planetService.updatePlanetById(id, planetRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit planet", planetResponse), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePlanetById(@PathVariable String id){
        boolean deleted = planetService.deletePlanetById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete planet" : "planet not found"), HttpStatus.OK
        );
    }
}
