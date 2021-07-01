package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.AccountRequest;
import com.synrgybootcamp.project.web.model.request.ContactRequest;
import com.synrgybootcamp.project.web.model.response.AccountResponse;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getAllAccounts(String keyword);
    List<AccountResponse> recentAccounts();
    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse getAccountById(String id);
    AccountResponse updateAccountById(String id, AccountRequest accountRequest);
    boolean deleteAccountById(String id);
}
