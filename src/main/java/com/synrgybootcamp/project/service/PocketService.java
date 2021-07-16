package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.enums.PocketTransactionType;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import com.synrgybootcamp.project.web.model.response.PocketTransactionResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PocketService {
    PocketResponse createPocket(PocketRequest pocketRequest);
    List<PocketResponse> getAllPocket(boolean withPrimary);
    PocketResponse getDetailPocketByID(String id);
    PocketResponse updatePocketById(String id,PocketRequest pocketRequest);
    boolean deletePocketById(String id);
    List<PocketTransactionResponse> getHistory(String pocketId, Sort sort, PocketTransactionType type);
    TopUpPocketBalanceResponse topUpBalance(String userId, TopUpPocketBalanceRequest payload);
    MovePocketBalanceResponse moveBalance(MovePocketBalanceRequest payload);
}
