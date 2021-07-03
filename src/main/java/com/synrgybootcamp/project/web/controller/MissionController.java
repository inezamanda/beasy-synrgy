package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.MissionService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.web.model.request.MissionRequest;
import com.synrgybootcamp.project.web.model.response.MissionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mission")
@Api(tags = "Mission", description = "Mission Controller (Admin Only)")
public class MissionController {

    @Autowired
    private MissionService missionService;

    @GetMapping("")
    @ApiOperation(value = "Get list of missions (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<MissionResponse>> listMission() {
        List<MissionResponse> missionResponses = missionService.getAllMission();

        return new ApiResponse<>("success get all missions", missionResponses);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get detail of the mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<MissionResponse> getMissionById(@PathVariable String id){
        MissionResponse missionResponse = missionService.getMissionById(id);

        return new ApiResponse<>("success get detail mission", missionResponse);
    }

    @PostMapping("")
    @ApiOperation(value = "Add new mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<MissionResponse> addMission(@RequestBody MissionRequest missionRequest){
        MissionResponse missionResponse = missionService.createMission(missionRequest);

        return new ApiResponse<>("success add new mission", missionResponse);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Edit mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<MissionResponse> updateMissionById(
            @PathVariable String id,
            @RequestBody MissionRequest missionRequest
    ){
        MissionResponse missionResponse = missionService.updateMissionById(id, missionRequest);

        return new ApiResponse<>("success edit mission", missionResponse);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete mission (Admin Only)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponseWithoutData deleteMissionById(@PathVariable String id){
        missionService.deleteMissionById(id);

        return new ApiResponseWithoutData("success delete mission");
    }
}
