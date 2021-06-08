package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.UserBalanceResponse;

public interface UserBalanceService {
    UserBalanceResponse getUserBalance(String userId);
}
