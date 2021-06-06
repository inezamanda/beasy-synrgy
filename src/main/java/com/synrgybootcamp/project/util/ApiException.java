package com.synrgybootcamp.project.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private HttpStatus status;
    private ErrorResponse body;

    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.body = new ErrorResponse(false, message);
    }
}
