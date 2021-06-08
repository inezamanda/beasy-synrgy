package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PocketService {
    Page<PocketResponse> getAllPocket(Pageable page);
}
