package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springfox.documentation.annotations.ApiIgnore;


@ControllerAdvice
@ApiIgnore
public class ErrorController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorResponse> exception(ApiException exception) {
        return new ResponseEntity<>(exception.getBody(), exception.getStatus());
    }
}
