package com.synrgybootcamp.project.web.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpRequest {
    private String name;
    private String message;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String email;
}
