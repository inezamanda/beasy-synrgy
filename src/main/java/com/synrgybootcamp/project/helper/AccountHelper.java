package com.synrgybootcamp.project.helper;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.Account;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.web.model.response.AccountResponse;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountHelper {
    public static List<Account> fetchRecentAccount(List<Account> accounts) {
        List<Account> recentAccounts = new ArrayList<>();

        accounts.stream().forEach(account -> {
            if (recentAccounts.size() < 3) {
                if (!CollectionUtils.contains(recentAccounts.iterator(), account)) {
                    recentAccounts.add(account);
                }
            }
        });

        return recentAccounts;
    }

    public static List<AccountResponse> toWebResponse(List<Account> accounts) {
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

    public static List<Account> getFromTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getEwalletTransaction().getAccount() != null)
                .map(transaction -> transaction.getEwalletTransaction().getAccount())
                .collect(Collectors.toList());
    }
}
