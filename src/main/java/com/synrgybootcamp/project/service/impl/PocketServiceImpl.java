package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PocketServiceImpl implements PocketService {
    @Autowired
    private PocketRepository pocketRepository;

    @Override
    public Page<PocketResponse> getAllPocket(Pageable page) {
        Page<Pocket> pagePocket = pocketRepository.findAll(page);
        return new PageImpl<>(
                pagePocket.stream().map(pocket -> PocketResponse.builder().id(pocket.getId())
                .picture(pocket.getPicture()).pocket_name(pocket.getName()).target(pocket.getTarget())
                .primary(pocket.getPrimary()).balance(pocket.getBalance()).build())
                .collect(Collectors.toList())
        );

    }
}
