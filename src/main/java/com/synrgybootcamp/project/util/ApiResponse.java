package com.synrgybootcamp.project.util;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private final Boolean success = true;
    private String message;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
