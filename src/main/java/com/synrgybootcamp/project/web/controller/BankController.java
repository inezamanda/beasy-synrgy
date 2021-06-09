package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.BankServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bank")
public class BankController {
    @Autowired
    private BankServiceImpl bankService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllItems() {
        List<BankResponse> pagedBanks = bankService.getAllBanks(
        );

        return new ResponseEntity<>(
                new ApiResponse("success get banks data", pagedBanks),
                HttpStatus.OK
        );
    }
}
