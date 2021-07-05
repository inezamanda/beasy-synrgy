package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGamificationStatusResponse {

  @JsonProperty("planet_id")
  String planetId;

  @JsonProperty("planet_name")
  String planetName;

  @JsonProperty("planet_image")
  String planetImage;

  @JsonProperty("planet_sequence")
  Integer planetSequence;

  @JsonProperty("planet_wording")
  String planetWording;

  @JsonProperty("is_on_completion_delay")
  boolean onCompletionDelay;

  @JsonProperty("last_planet_completion_delay_finished")
  Date lastPlanetCompletionDelayFinished;
}
