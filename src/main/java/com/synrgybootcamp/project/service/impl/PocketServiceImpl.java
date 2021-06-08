package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.PocketTransaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.PocketAction;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.PocketTransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PocketServiceImpl implements PocketService {

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private PocketTransactionRepository pocketTransactionRepository;

    @Override
    @Transactional
    public TopUpPocketBalanceResponse topUpBalance(String userId, TopUpPocketBalanceRequest payload) {
        Pocket sourcePocket = changePocketBalance(
                pocketRepository.findPrimaryPocket(userId)
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket utama tidak ditemukan")),
                payload.getAmount(),
                PocketAction.DECREASE
        );

        Pocket destinationPocket = changePocketBalance(
                pocketRepository.findById(payload.getDestination())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket tujuan tidak ditemukan")),
                payload.getAmount(),
                PocketAction.INCREASE
        );

        PocketTransaction transactionResult = createPocketTransaction(sourcePocket, destinationPocket, payload.getAmount());

        return TopUpPocketBalanceResponse
                .builder()
                .pocketDestinationId(transactionResult.getDestinationPocket().getId())
                .pocketDestinationName(transactionResult.getDestinationPocket().getName())
                .pocketDestinationBalance(transactionResult.getDestinationPocket().getBalance())
                .amount(transactionResult.getAmount())
                .build();
    }

    @Override
    @Transactional
    public MovePocketBalanceResponse moveBalance(MovePocketBalanceRequest payload) {
        Pocket sourcePocket = changePocketBalance(
                pocketRepository.findById(payload.getSource())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket sumber dana tidak ditemukan")),
                payload.getAmount(),
                PocketAction.DECREASE
        );

        Pocket destinationPocket = changePocketBalance(
                pocketRepository.findById(payload.getDestination())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "pocket tujuan tidak ditemukan")),
                payload.getAmount(),
                PocketAction.INCREASE
        );

        PocketTransaction transactionResult = createPocketTransaction(sourcePocket, destinationPocket, payload.getAmount());

        return MovePocketBalanceResponse
                .builder()
                .pocketSourceId(transactionResult.getSourcePocket().getId())
                .pocketSourceName(transactionResult.getSourcePocket().getName())
                .pocketSourceBalance(transactionResult.getSourcePocket().getBalance())
                .pocketDestinationId(transactionResult.getDestinationPocket().getId())
                .pocketDestinationName(transactionResult.getDestinationPocket().getName())
                .pocketDestinationBalance(transactionResult.getDestinationPocket().getBalance())
                .amount(transactionResult.getAmount())
                .build();
    }

    private Pocket changePocketBalance(Pocket pocket, Integer amount, PocketAction action) {
        Integer finalBalance = null;
        switch (action) {
            case INCREASE:
                finalBalance = pocket.getBalance() + amount;
                break;
            case DECREASE:
                finalBalance = pocket.getBalance() - amount;
                if (finalBalance < 0)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Jumlah melebihi saldo");
                break;
            default:
                break;
        }

        pocket.setBalance(finalBalance);
        return pocketRepository.save(pocket);
    }

    private PocketTransaction createPocketTransaction(Pocket source, Pocket destination, Integer amount) {
        return pocketTransactionRepository.save(
                PocketTransaction
                        .builder()
                        .user(source.getUser())
                        .sourcePocket(source)
                        .destinationPocket(destination)
                        .amount(amount)
                        .build()
        );
    }
}
