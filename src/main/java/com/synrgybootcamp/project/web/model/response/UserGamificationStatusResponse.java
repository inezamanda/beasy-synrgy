package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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

  @JsonProperty("next_planet_id")
  String nextPlanetId;

  @JsonProperty("next_planet_name")
  String nextPlanetName;

  @JsonProperty("next_planet_image")
  String nextPlanetImage;

  @JsonProperty("next_planet_sequence")
  Integer nextPlanetSequence;

  @JsonProperty("next_planet_wording")
  String nextPlanetWording;

  @JsonProperty("is_on_last_planet")
  boolean onLastPlanet;

  @JsonProperty("is_completed_gamification")
  boolean completedGamification;

  @JsonProperty("is_on_completion_delay")
  boolean onCompletionDelay;

  @JsonProperty("recent_planet_completion_delay_finished")
  Date recentPlanetCompletionDelayFinished;
}
