package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synrgybootcamp.project.enums.PlanetStatus;
import com.synrgybootcamp.project.web.model.response.sub.MissionObjResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDetailResponse {

  String id;

  String name;

  Integer sequence;

  String image;

  String storytelling;

  String wording;

  List<MissionObjResponse> mission;

  @JsonProperty
  String rewardId;

  PlanetStatus status;
}
