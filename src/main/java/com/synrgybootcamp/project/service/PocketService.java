package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PocketService {
    List<PocketResponse> getAllPocket();
    PocketResponse getDetailPocketByID(@PathVariable String id);
    PocketResponse createPocket(PocketRequest pocketRequest);
}
