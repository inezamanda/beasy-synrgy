package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.*;
import com.synrgybootcamp.project.enums.EwalletStatus;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.*;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.EwalletTransactionService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.EwalletTransactionRequest;
import com.synrgybootcamp.project.web.model.response.EwalletTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.TimeZone;

@Service
public class EwalletTransactionServiceImpl implements EwalletTransactionService {
    @Autowired
    EwalletTransactionRepository ewalletTransactionRepository;

    @Autowired
    PocketRepository pocketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInformation userInformation;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    @Transactional
    public EwalletTransactionResponse ewalletTransaction(EwalletTransactionRequest ewalletTransactionRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "user not found"));

        Pocket pocket = pocketRepository.findPrimaryPocket(user.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user primary pocket not found"));

        Account account = accountRepository.findById(ewalletTransactionRequest.getAccountId())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "account not found"));

        if (!(ewalletTransactionRequest.getPin().equals(user.getPin())))
            throw new ApiException(HttpStatus.NOT_FOUND, "wrong pin");

        Integer adminFee = TransactionConstants.ADMIN_FEE;

        Integer finalBalance = pocket.getBalance() - (ewalletTransactionRequest.getAmount() + adminFee);

        if (finalBalance < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Jumlah melebihi saldo");

        pocket.setBalance(finalBalance);
        pocketRepository.save(pocket);

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        Date date = new Date();
        Transaction transaction = transactionRepository.save(
                Transaction
                        .builder()
                        .description("E-wallet (" + account.getEwallet().getName() + "-" + account.getAccountNumber() + ")")
                        .user(user)
                        .totalAmount(ewalletTransactionRequest.getAmount() + adminFee)
                        .type(TransactionType.EWALLET)
                        .date(date)
                        .build()
        );

        EwalletTransaction ewalletTransaction = ewalletTransactionRepository.save(
                EwalletTransaction.builder()
                        .status(EwalletStatus.SUCCESS)
                        .adminFee(adminFee)
                        .amount(ewalletTransactionRequest.getAmount())
                        .account(account)
                        .message(ewalletTransactionRequest.getMessage())
                        .user(user)
                        .transaction(transaction)
                        .build()
        );

        return EwalletTransactionResponse
                .builder()
                .status(EwalletStatus.SUCCESS.name())
                .ewalletName(account.getEwallet().getName())
                .accountName(account.getName())
                .accountNumber(account.getAccountNumber())
                .amount(ewalletTransaction.getAmount())
                .message(ewalletTransaction.getMessage())
                .on(transaction.getDate())
                .totalTransfer(ewalletTransaction.getAmount() + ewalletTransaction.getAdminFee())
                .refCode(transaction.getId())
                .build();
    }
}
