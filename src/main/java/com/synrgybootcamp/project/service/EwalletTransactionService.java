package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.EwalletTransactionRequest;
import com.synrgybootcamp.project.web.model.response.EwalletTransactionResponse;

public interface EwalletTransactionService {
    EwalletTransactionResponse ewalletTransaction(EwalletTransactionRequest ewalletTransactionRequest);
}
