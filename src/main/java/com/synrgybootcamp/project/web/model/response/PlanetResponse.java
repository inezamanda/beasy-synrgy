package com.synrgybootcamp.project.web.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanetResponse {
    String id;

    String name;

    String image;

    Integer sequence;

    @JsonProperty("story_telling")
    String storyTelling;

    String wording;
}
