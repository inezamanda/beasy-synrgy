package com.synrgybootcamp.project.web.model.response;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileCreditResponse {
    private String id;
    private String denom;
    private Integer price;
}
