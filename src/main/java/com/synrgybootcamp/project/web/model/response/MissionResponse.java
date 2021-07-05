package com.synrgybootcamp.project.web.model.response;

import com.synrgybootcamp.project.entity.Planet;
import com.synrgybootcamp.project.enums.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionResponse {
    String id;
    MissionType missionType;
    Integer target;
    String wording;
    String planetId;
}
