package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PocketServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pocket")
public class PocketController {
    @Autowired
    private PocketServiceImpl pocketService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllPockets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PocketResponse> pagePocket = pocketService.getAllPocket(
                PageRequest.of(page, size)
        );

        return new ResponseEntity<>(
                new ApiResponse("get pocket success", pagePocket),
                HttpStatus.OK
        );
    }
}
