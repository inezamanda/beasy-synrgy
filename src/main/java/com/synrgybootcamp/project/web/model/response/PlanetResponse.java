package com.synrgybootcamp.project.web.model.response;


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
    String story_telling;
    Integer sequence;
}
