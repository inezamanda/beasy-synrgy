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
public class DetailRewardResponse {

    String id;

    RewardPlanetType type;

    String wording;

    String tnc;

    @JsonProperty( "is_claimed")
    Boolean claimed;

}
