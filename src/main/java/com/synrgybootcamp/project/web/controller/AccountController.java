package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.AccountServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.AccountRequest;
import com.synrgybootcamp.project.web.model.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping
    public ResponseEntity<ApiResponse> listAccounts(
            @RequestParam(required = false) String keyword
    ) {
        List<AccountResponse> accounts = accountService.getAllAccounts(keyword);
        return new ResponseEntity<>(
                new ApiResponse("success get all accounts", accounts), HttpStatus.OK
        );
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> recentAccounts(){
        List<AccountResponse> accounts = accountService.recentAccounts();
        return new ResponseEntity<>(
                new ApiResponse("success get all accounts", accounts), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addAccount(@RequestBody AccountRequest accountRequest){
        AccountResponse account = accountService.createAccount(accountRequest);
        return new ResponseEntity<>(
                new ApiResponse("success add new account", account), HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAccountById(
            @PathVariable String id){
        AccountResponse detailAccount = accountService.getAccountById(id);

        return new ResponseEntity<>(
                new ApiResponse("success get detail account", detailAccount), HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAccountById(
            @PathVariable String id,
            @RequestBody AccountRequest accountRequest){
        AccountResponse account = accountService.updateAccountById(id, accountRequest);
        return new ResponseEntity<>(
                new ApiResponse("success edit account", account), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAccountById(@PathVariable String id){
        boolean deleted = accountService.deleteAccountById(id);

        return new ResponseEntity<>(
                new ApiResponse(deleted ? "success delete account" : "account not found"), HttpStatus.OK
        );
    }
}
