package com.synrgybootcamp.project.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlanetRequest {
    String name;

    MultipartFile image;

    Integer sequence;

    String storyTelling;

    String wording;
}
