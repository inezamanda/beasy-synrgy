package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.*;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.*;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.AccountService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.AccountRequest;
import com.synrgybootcamp.project.web.model.response.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EwalletRepository ewalletRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserInformation userInformation;

    @Override
    public List<AccountResponse> getAllAccounts(String keyword) {
        List<Account> accounts;

        User loggedInUser = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (keyword == null) {
            accounts = accountRepository.findByUserAndDeleteIsFalse(loggedInUser);
        } else {
            List<Account> accountsResultByName = accountRepository.
                    findByNameIsContainingIgnoreCaseAndUserAndDeleteIsFalse(keyword, loggedInUser);

            List<String> ids = Optional.ofNullable(accountsResultByName)
                    .filter(val -> !CollectionUtils.isEmpty(val))
                    .map(data -> data.stream().map(Account::getId).collect(Collectors.toList()))
                    .orElse(Arrays.asList("-"));

            List<Account> accountsResultByAccountNumber = accountRepository
                    .findByAccountNumberContainingIgnoreCaseAndUserAndIdNotInAndDeleteIsFalse(keyword, loggedInUser, ids);

            accounts = Stream
                    .concat(accountsResultByName.stream(), accountsResultByAccountNumber.stream())
                    .collect(Collectors.toList());
        }

        return toWebResponse(accounts);
    }

    @Override
    public List<AccountResponse> recentAccounts() {
        User loggedInUser = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        return Optional.ofNullable(transactionRepository
                .findByUserAndTypeOrderByDateDesc(loggedInUser, TransactionType.EWALLET))
                .map(this::getFromTransaction)
                .map(this::fetchRecentAccount)
                .map(this::toWebResponse)
                .orElse(new ArrayList<>());
    }

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Ewallet ewallet = ewalletRepository.findById(accountRequest.getEwalletId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Ewallet tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (accountRepository.existsByUserAndNameAndDeleteIsFalseOrUserAndAccountNumberAndDeleteIsFalse(user, accountRequest.getName(), user, accountRequest.getAccountNumber())){
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is a account with the same name / account number");
        }

        Account account = accountRepository.save(
                Account.builder()
                        .name(accountRequest.getName())
                        .accountNumber(accountRequest.getAccountNumber())
                        .ewallet(ewallet)
                        .user(user)
                        .delete(false)
                        .build()
        );

        return AccountResponse.builder()
                .id(account.getId())
                .name(account.getName())
                .accountNumber(account.getAccountNumber())
                .ewalletId(ewallet.getId())
                .ewalletName(ewallet.getName())
                .adminFee(TransactionConstants.ADMIN_FEE)
                .build();
    }

    @Override
    public AccountResponse getAccountById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Account tidak ditemukan"));

        return AccountResponse
                .builder()
                .id(account.getId())
                .name(account.getName())
                .accountNumber(account.getAccountNumber())
                .ewalletId(account.getEwallet().getId())
                .ewalletName(account.getEwallet().getName())
                .adminFee(TransactionConstants.ADMIN_FEE)
                .build();
    }

    @Override
    public AccountResponse updateAccountById(String id, AccountRequest accountRequest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Account tidak ditemukan"));

        Ewallet ewallet = ewalletRepository.findById(accountRequest.getEwalletId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Ewallet tidak ditemukan"));

        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "User tidak ditemukan"));

        if (accountRepository.existsByUserAndNameAndIdNotAndDeleteIsFalseOrUserAndAccountNumberAndIdNotAndDeleteIsFalse(user, accountRequest.getName(), id, user, accountRequest.getAccountNumber(), id)){
            throw new ApiException(HttpStatus.BAD_REQUEST, "There is a account with the same name / account number");
        }

        account.setName(accountRequest.getName());
        account.setAccountNumber(accountRequest.getAccountNumber());
        account.setEwallet(ewallet);

        Account accountResult = accountRepository.save(account);

        return AccountResponse
                .builder()
                .id(accountResult.getId())
                .name(accountResult.getName())
                .accountNumber(accountResult.getAccountNumber())
                .ewalletId(ewallet.getId())
                .ewalletName(ewallet.getName())
                .adminFee(TransactionConstants.ADMIN_FEE)
                .build();
    }

    @Override
    public boolean deleteAccountById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "Account tidak ditemukan"));

        account.setDelete(true);
        accountRepository.save(account);
        return true;
    }

    private List<Account> fetchRecentAccount(List<Account> accounts) {
        List<Account> recentAccounts = new ArrayList<>();

        accounts.stream().forEach(account -> {
            if (recentAccounts.size() < 3) {
                if (!CollectionUtils.contains(recentAccounts.iterator(), account) && !account.getDelete()) {
                    recentAccounts.add(account);
                }
            }
        });

        return recentAccounts;
    }

    private List<AccountResponse> toWebResponse(List<Account> accounts) {
        return accounts.stream()
            .map(account -> AccountResponse
                .builder()
                .id(account.getId())
                .name(account.getName())
                .accountNumber(account.getAccountNumber())
                .ewalletId(account.getEwallet().getId())
                .ewalletName(account.getEwallet().getName())
                .adminFee(TransactionConstants.ADMIN_FEE)
                .build()
            ).collect(Collectors.toList());
    }

    private List<Account> getFromTransaction(List<Transaction> transactions) {
        return transactions.stream()
            .filter(transaction -> transaction.getEwalletTransaction().getAccount() != null)
            .map(transaction -> transaction.getEwalletTransaction().getAccount())
            .collect(Collectors.toList());
    }
}
