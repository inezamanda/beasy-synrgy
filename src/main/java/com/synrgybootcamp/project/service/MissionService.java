package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MissionRequest;
import com.synrgybootcamp.project.web.model.response.MissionResponse;

import java.util.List;

public interface MissionService {
    List<MissionResponse> getAllMission();
    MissionResponse createMission(MissionRequest missionRequest);
    MissionResponse getMissionById(String id);
    MissionResponse updateMissionById(String id, MissionRequest missionRequest);
    boolean deleteMissionById(String id);
}
