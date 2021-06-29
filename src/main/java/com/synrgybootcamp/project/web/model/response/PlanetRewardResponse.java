package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synrgybootcamp.project.enums.RewardPlanetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetRewardResponse {

  String id;

  RewardPlanetType type;

  String wording;

  String tnc;

  Integer amount;

  @JsonProperty("planet_id")
  String planetId;

  @JsonProperty("planet_name")
  String planetName;

}
