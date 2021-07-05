package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.AccountServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.ApiResponseWithoutData;
import com.synrgybootcamp.project.web.model.request.AccountRequest;
import com.synrgybootcamp.project.web.model.response.AccountResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
@Api(tags = "Accounts", description = "Accounts Controller")
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get list of accounts")
    public ApiResponse<List<AccountResponse>> listAccounts(
            @RequestParam(required = false) String keyword
    ) {
        List<AccountResponse> accounts = accountService.getAllAccounts(keyword);

        return new ApiResponse<>("success get all accounts", accounts);
    }

    @GetMapping("/recent")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get recent account")
    public ApiResponse<List<AccountResponse>> recentAccounts(){
        List<AccountResponse> accounts = accountService.recentAccounts();

        return new ApiResponse<>("success get all accounts", accounts);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Add new account")
    public ApiResponse<AccountResponse> addAccount(@RequestBody AccountRequest accountRequest){
        AccountResponse account = accountService.createAccount(accountRequest);

        return new ApiResponse<>("success add new account", account);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get detail account")
    public ApiResponse<AccountResponse> getAccountById(
            @PathVariable String id){
        AccountResponse detailAccount = accountService.getAccountById(id);

        return new ApiResponse<>("success get detail account", detailAccount);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Edit account")
    public ApiResponse<AccountResponse> updateAccountById(
            @PathVariable String id,
            @RequestBody AccountRequest accountRequest){
        AccountResponse account = accountService.updateAccountById(id, accountRequest);

        return new ApiResponse<>("success edit account", account);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Delete Account")
    public ApiResponseWithoutData deleteAccountById(@PathVariable String id){
        accountService.deleteAccountById(id);

        return new ApiResponseWithoutData("success delete account");
    }
}
