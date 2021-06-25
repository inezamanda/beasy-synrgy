package com.synrgybootcamp.project.web.controller;


import com.synrgybootcamp.project.service.impl.MissionServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.MissionRequest;
import com.synrgybootcamp.project.web.model.request.PlanetRequest;
import com.synrgybootcamp.project.web.model.response.MissionResponse;
import com.synrgybootcamp.project.web.model.response.PlanetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mission")
public class MissionController {

    @Autowired
    private MissionServiceImpl missionService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> listMission() {
        List<MissionResponse> missionResponses = missionService.getAllMission();
        return new ResponseEntity<>(
                new ApiResponse("success get all missions", missionResponses), HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMissionById(
            @PathVariable String id){
        MissionResponse missionResponse = missionService.getMissionById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail mission", missionResponse), HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addMission(@RequestBody MissionRequest missionRequest){
        MissionResponse missionResponse = missionService.createMission(missionRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new mission", missionResponse), HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMissionById(
            @PathVariable String id,
            @RequestBody MissionRequest missionRequest){
        MissionResponse missionResponse = missionService.updateMissionById(id, missionRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit mission", missionResponse), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMissionById(@PathVariable String id){
        boolean deleted = missionService.deleteMissionById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete mission" : "mission not found"), HttpStatus.OK
        );
    }
}
