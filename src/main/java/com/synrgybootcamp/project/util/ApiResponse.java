package com.synrgybootcamp.project.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private Boolean success = true;
    private String message;
    private Object data;
    private Pagination pagination;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, Object data, Pagination pagination) {
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
}
