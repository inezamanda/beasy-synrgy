package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferHistoryResponse;
import com.synrgybootcamp.project.web.model.response.TransferResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TransferService {
    TransferResponse transfer(TransferRequest transferRequest);
    List<TransferHistoryResponse> getHistory(Sort sort);
}
