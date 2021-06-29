package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.TransactionServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.response.RecentTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("payment/recent")
    public ResponseEntity<ApiResponse> getRecentPaymentTransaction() {
        List<RecentTransactionResponse> recentTransactionResponses = transactionService.recentTransaction();
        return new ResponseEntity<>(
                new ApiResponse("Successfully get recent payment transaction", recentTransactionResponses)
                , HttpStatus.OK
        );
    }
}
