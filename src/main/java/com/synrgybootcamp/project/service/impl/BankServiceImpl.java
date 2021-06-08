package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Bank;
import com.synrgybootcamp.project.repository.BankRepository;
import com.synrgybootcamp.project.service.BankService;
import com.synrgybootcamp.project.web.model.response.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepository;

    @Override
    public Page<BankResponse> getAllBanks(Pageable page) {
        Page<Bank> pagedBank = bankRepository.findAll(page);
        return new PageImpl<>(
                pagedBank
                        .stream()
                        .map(bank -> BankResponse
                                .builder()
                                .bank_id(bank.getId())
                                .bank_name(bank.getName())
                                .build())
                        .collect(Collectors.toList())
        );
    }
}
