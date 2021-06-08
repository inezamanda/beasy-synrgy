package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.BankResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankService {
    Page<BankResponse> getAllBanks(Pageable Page);
}
