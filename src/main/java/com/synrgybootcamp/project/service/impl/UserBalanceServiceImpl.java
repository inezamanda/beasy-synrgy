package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.service.UserBalanceService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.response.UserBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    @Autowired
    PocketRepository pocketRepository;

    @Override
    public UserBalanceResponse getUserBalance(String userId) {
        Pocket pocket = pocketRepository.findPrimaryPocket(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket utama tidak ditemukan"));

        return UserBalanceResponse
                .builder()
                .userId(userId)
                .balance(pocket.getBalance())
                .build();
    }
}
