package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.EwalletServiceImpl;
import com.synrgybootcamp.project.service.impl.EwalletTransactionServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.EwalletTransactionRequest;
import com.synrgybootcamp.project.web.model.response.EwalletResponse;
import com.synrgybootcamp.project.web.model.response.EwalletTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ewallet")
public class EwalletController {
    @Autowired
    private EwalletServiceImpl ewalletService;

    @Autowired
    EwalletTransactionServiceImpl ewalletTransactionService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getAllEwallets() {
        List<EwalletResponse> ewallets = ewalletService.getAllEwallets(
        );

        return new ResponseEntity<>(
                new ApiResponse("success get ewallets data", ewallets), HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<Object> ewalletTransaction(@RequestBody EwalletTransactionRequest ewalletTransactionRequest) {
        EwalletTransactionResponse ewalletResult = ewalletTransactionService.ewalletTransaction(
                EwalletTransactionRequest
                        .builder()
                        .accountId(ewalletTransactionRequest.getAccountId())
                        .amount(ewalletTransactionRequest.getAmount())
                        .message(ewalletTransactionRequest.getMessage())
                        .pin(ewalletTransactionRequest.getPin())
                        .build()
        );

        return new ResponseEntity<>(
                new ApiResponse("Ewallet transaction success", ewalletResult)
                , HttpStatus.OK
        );
    }
}
