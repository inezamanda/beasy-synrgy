package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Bank;
import com.synrgybootcamp.project.repository.BankRepository;
import com.synrgybootcamp.project.service.BankService;
import com.synrgybootcamp.project.web.model.response.BankResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<BankResponse> getAllBanks() {
        List<Bank> pagedBank = bankRepository.findAll();
        return pagedBank
                .stream()
                .map(bank -> BankResponse
                        .builder()
                        .bankId(bank.getId())
                        .bankName(bank.getName())
                        .build())
                .collect(Collectors.toList()
                );
    }
}
