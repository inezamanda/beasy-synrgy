package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpResponse {
    private String id;
    private String name;
    private String message;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;
}
