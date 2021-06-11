package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferResponse;

public interface TransferService {
    TransferResponse transfer(TransferRequest transferRequest);
}
