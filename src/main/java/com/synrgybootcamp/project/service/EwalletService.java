package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.EwalletResponse;

import java.util.List;

public interface EwalletService {
    List<EwalletResponse> getAllEwallets();
}
