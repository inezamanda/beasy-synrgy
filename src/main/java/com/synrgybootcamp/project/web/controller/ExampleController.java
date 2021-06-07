package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.ExampleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/example")
public class ExampleController {
    @Autowired
    private UserInformation userInformation;

    @GetMapping("success")
    public ResponseEntity<ApiResponse> exampleSuccessAPI() {
        ExampleResponse exampleData = ExampleResponse
                .builder()
                .example("contoh")
                .user_id(userInformation.getUserID()) // Dapetin user_id
                .build();

        return new ResponseEntity<>(
                new ApiResponse("example success api", exampleData),
                HttpStatus.OK
        );

    }

    @GetMapping("error")
    public ResponseEntity<ApiResponse> exampleErrorAPI() {
        throw new ApiException(HttpStatus.BAD_REQUEST, "contoh error handling");
    }

}
