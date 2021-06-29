package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.RecentTransactionResponse;

import java.util.List;

public interface TransactionService {
    List<RecentTransactionResponse> recentTransaction();
}
