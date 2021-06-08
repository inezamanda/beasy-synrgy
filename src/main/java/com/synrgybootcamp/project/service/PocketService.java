package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;

public interface PocketService {
    TopUpPocketBalanceResponse topUpBalance(String userId, TopUpPocketBalanceRequest payload);
    MovePocketBalanceResponse moveBalance(MovePocketBalanceRequest payload);
}
