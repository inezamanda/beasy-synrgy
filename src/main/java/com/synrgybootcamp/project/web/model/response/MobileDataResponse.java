package com.synrgybootcamp.project.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileDataResponse {
    private String id;
    private String name;
    private String description;
    private Integer price;
}
