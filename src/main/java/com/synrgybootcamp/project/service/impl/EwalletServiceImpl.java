package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Ewallet;
import com.synrgybootcamp.project.repository.EwalletRepository;
import com.synrgybootcamp.project.service.EwalletService;
import com.synrgybootcamp.project.web.model.response.EwalletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EwalletServiceImpl implements EwalletService {
    @Autowired
    private EwalletRepository ewalletRepository;

    @Override
    public List<EwalletResponse> getAllEwallets() {
        List<Ewallet> ewallets = ewalletRepository.findAll();
        return ewallets
                .stream()
                .map(ewallet -> EwalletResponse
                        .builder()
                        .id(ewallet.getId())
                        .name(ewallet.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
