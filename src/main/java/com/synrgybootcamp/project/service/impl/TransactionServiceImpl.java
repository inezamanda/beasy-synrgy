package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.TransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.TransactionService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.response.RecentTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<RecentTransactionResponse> recentTransaction() {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        List<TransactionType> transactionTypes = Arrays.asList(TransactionType.PAYMENT_MERCHANT, TransactionType.PAYMENT_MOBILE, TransactionType.PAYMENT_CREDIT_CARD);
        List<Transaction> transactions = transactionRepository.findByUserAndTypeInOrderByDateDesc(user, transactionTypes);
        return transactions
                .stream()
                .limit(5)
                .map(transaction -> RecentTransactionResponse
                .builder()
                        .id(transaction.getId())
                        .transactionType(transaction.getType().getTextDisplay())
                        .amount(transaction.getTotalAmount())
                        .description(transaction.getDescription())
                        .on(transaction.getDate())
                .build())
                .collect(Collectors.toList());
    }
}
