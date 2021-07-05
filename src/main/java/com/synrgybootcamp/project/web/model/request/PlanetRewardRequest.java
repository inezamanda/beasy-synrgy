package com.synrgybootcamp.project.web.model.request;

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
public class PlanetRewardRequest {

  RewardPlanetType type;

  String wording;

  String tnc;

  Integer amount;

  @JsonProperty("planet_id")
  String planetId;

}
