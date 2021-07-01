package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.BankServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.BankResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/bank")
@Api(tags = "Bank", description = "Bank Controller")
public class BankController {
    @Autowired
    private BankServiceImpl bankService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get list of Bank")
    public ResponseEntity<ApiResponse> getAllItems() {
        List<BankResponse> pagedBanks = bankService.getAllBanks(
        );

        return new ResponseEntity<>(
                new ApiResponse("success get banks data", pagedBanks),
                HttpStatus.OK
        );
    }
}
