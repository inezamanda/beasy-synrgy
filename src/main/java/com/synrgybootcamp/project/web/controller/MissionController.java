package com.synrgybootcamp.project.web.controller;


import com.synrgybootcamp.project.service.impl.MissionServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.MissionRequest;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.MissionResponse;
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
@RequestMapping("api/mission")
@Api(tags = "Mission", description = "Mission Controller (Admin Only)")
public class MissionController {

    @Autowired
    private MissionServiceImpl missionService;

    @GetMapping("")
    @ApiOperation(value = "Get list of missions (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> listMission() {
        List<MissionResponse> missionResponses = missionService.getAllMission();
        return new ResponseEntity<>(
                new ApiResponse("success get all missions", missionResponses), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of the mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getMissionById(
            @PathVariable String id){
        MissionResponse missionResponse = missionService.getMissionById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail mission", missionResponse), HttpStatus.OK
        );
    }

    @PostMapping("")
    @ApiOperation(value = "Add new mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> addMission(@RequestBody MissionRequest missionRequest){
        MissionResponse missionResponse = missionService.createMission(missionRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new mission", missionResponse), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateMissionById(
            @PathVariable String id,
            @RequestBody MissionRequest missionRequest){
        MissionResponse missionResponse = missionService.updateMissionById(id, missionRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit mission", missionResponse), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteMissionById(@PathVariable String id){
        boolean deleted = missionService.deleteMissionById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete mission" : "mission not found"), HttpStatus.OK
        );
    }
}
