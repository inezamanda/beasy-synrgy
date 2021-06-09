package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.BankResponse;

import java.util.List;

public interface BankService {
    List<BankResponse> getAllBanks();
}
