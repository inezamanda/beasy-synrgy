package com.synrgybootcamp.project.web.model.response.sub;

import com.synrgybootcamp.project.enums.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionObjResponse {

  String id;

  String wording;

  MissionType missionType;

  boolean passed;
}
