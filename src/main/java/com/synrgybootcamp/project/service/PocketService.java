package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PocketService {
    TopUpPocketBalanceResponse topUpBalance(String userId, TopUpPocketBalanceRequest payload);
    MovePocketBalanceResponse moveBalance(MovePocketBalanceRequest payload);
    List<PocketResponse> getAllPocket(String userId);
    PocketResponse getDetailPocketByID(@PathVariable String id);
    PocketResponse createPocket(PocketRequest pocketRequest);
    PocketResponse updatePocketById(@PathVariable String id,PocketRequest pocketRequest);
    boolean deletePocketById(String id);

}
