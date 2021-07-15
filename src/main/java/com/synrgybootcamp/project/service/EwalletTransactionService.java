package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.EwalletTransactionRequest;
import com.synrgybootcamp.project.web.model.response.EwalletTransactionHistoryResponse;
import com.synrgybootcamp.project.web.model.response.EwalletTransactionResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EwalletTransactionService {
    EwalletTransactionResponse ewalletTransaction(EwalletTransactionRequest ewalletTransactionRequest);
    List<EwalletTransactionHistoryResponse> getHistory(Sort sort);
}
