package com.synrgybootcamp.project.web.model.request;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.enums.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MissionRequest {
    MissionType missionType;
    Integer target;
    String wording;
    String planetId;
}
