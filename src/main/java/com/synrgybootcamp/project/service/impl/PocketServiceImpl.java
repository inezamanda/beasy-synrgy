package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PocketServiceImpl implements PocketService {
    @Autowired
    private PocketRepository pocketRepository;

    @Override
    public List<PocketResponse> getAllPocket() {
        List<Pocket> listPocket = pocketRepository.findAll();
        return listPocket.stream().map(pocket -> PocketResponse.builder().id(pocket.getId())
                .picture(pocket.getPicture()).pocket_name(pocket.getName()).target(pocket.getTarget())
                .primary(pocket.getPrimary()).balance(pocket.getBalance()).build())
                .collect(Collectors.toList()
        );

    }

    @Override
    public PocketResponse getDetailPocketByID(String id) {
        Pocket pocket = pocketRepository.findById(id).orElse(null);

        return pocket == null
                ? null : PocketResponse.builder()
                .id(pocket.getId())
                .picture(pocket.getPicture())
                .pocket_name(pocket.getName())
                .target(pocket.getTarget())
                .primary(pocket.getPrimary())
                .balance(pocket.getBalance())
                .build();

    }

    @Override
    public PocketResponse createPocket(PocketRequest pocketRequest) {
        Pocket pocket = pocketRepository.save(
                Pocket.builder().name(pocketRequest.getName())
                .picture(pocketRequest.getPicture()).target(pocketRequest.getTarget())
                .build()
        );

        return PocketResponse.builder()
                .id(pocket.getId())
                .pocket_name(pocket.getName())
                .picture(pocket.getPicture())
                .target(pocket.getTarget())
                .build();
    }
}
