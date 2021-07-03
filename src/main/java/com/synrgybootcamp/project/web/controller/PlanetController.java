package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.PlanetService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/planet")
@Api(tags = "Planet", description = "Planet Controller (Admin Only)")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping("")
    @ApiOperation(value = "Get list of planets (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<PlanetResponse>> listPlanet() {
        List<PlanetResponse> planetResponses = planetService.getAllPlanet();

        return new ApiResponse<>("success get all planets", planetResponses);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<PlanetResponse> getPlanetById(@PathVariable String id){
        PlanetResponse planetResponse = planetService.getPlanetById(id);

        return new ApiResponse<>("success get detail planet", planetResponse);
    }

    @PostMapping("")
    @ApiOperation(value = "Add new planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<PlanetResponse> addPlanet(@ModelAttribute PlanetRequest planetRequest){
        PlanetResponse planetResponse = planetService.createPlanet(planetRequest);

        return new ApiResponse<>("success add new planet", planetResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<PlanetResponse> updatePlanetById(
            @PathVariable String id,
            @RequestBody PlanetRequest planetRequest
    ){
        PlanetResponse planetResponse = planetService.updatePlanetById(id, planetRequest);

        return new ApiResponse<>("success edit planet", planetResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete planet (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponseWithoutData deletePlanetById(@PathVariable String id){
        planetService.deletePlanetById(id);

        return new ApiResponseWithoutData("success delete planet");
    }
}
