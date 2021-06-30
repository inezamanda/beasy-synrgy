package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synrgybootcamp.project.enums.PlanetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListPlanetResponse {

  String id;

  String name;

  String image;

  Integer sequence;

  String wording;

  @JsonProperty("reward_id")
  String rewardId;

  PlanetStatus status;
}
