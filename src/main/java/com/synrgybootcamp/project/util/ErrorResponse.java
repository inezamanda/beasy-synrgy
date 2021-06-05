package com.synrgybootcamp.project.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    Boolean success = false;
    String message;

    public ErrorResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
