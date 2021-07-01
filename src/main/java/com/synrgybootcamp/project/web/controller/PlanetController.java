package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PlanetServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/planet")
@Api(tags = "Planet", description = "Planet Controller (Admin Only)")
public class PlanetController {

    @Autowired
    private PlanetServiceImpl planetService;

    @GetMapping("")
    @ApiOperation(value = "Get list of planets (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> listPlanet() {
        List<PlanetResponse> planetResponses = planetService.getAllPlanet();
        return new ResponseEntity<>(
                new ApiResponse("success get all planets", planetResponses), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getPlanetById(
            @PathVariable String id){
        PlanetResponse planetResponse = planetService.getPlanetById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail planet", planetResponse), HttpStatus.OK
        );
    }

    @PostMapping("")
    @ApiOperation(value = "Add new planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addPlanet(@ModelAttribute PlanetRequest planetRequest){
        PlanetResponse planetResponse = planetService.createPlanet(planetRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new planet", planetResponse), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updatePlanetById(
            @PathVariable String id,
            @RequestBody PlanetRequest planetRequest){
        PlanetResponse planetResponse = planetService.updatePlanetById(id, planetRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit planet", planetResponse), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deletePlanetById(@PathVariable String id){
        boolean deleted = planetService.deletePlanetById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete planet" : "planet not found"), HttpStatus.OK
        );
    }
}
