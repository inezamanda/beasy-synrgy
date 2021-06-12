package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.PocketTransaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.PocketAction;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.PocketTransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.service.UserDetailsServiceImpl;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PocketService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MovePocketBalanceRequest;
import com.synrgybootcamp.project.web.model.request.PocketRequest;
import com.synrgybootcamp.project.web.model.request.TopUpPocketBalanceRequest;
import com.synrgybootcamp.project.web.model.response.MovePocketBalanceResponse;
import com.synrgybootcamp.project.web.model.response.PocketResponse;
import com.synrgybootcamp.project.web.model.response.TopUpPocketBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PocketServiceImpl implements PocketService {


    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PocketTransactionRepository pocketTransactionRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UploadFileUtil uploadFileUtil;


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



    @Override
    public List<PocketResponse> getAllPocket(String userId) {
        List<Pocket> pocket = pocketRepository.findListPocket(userId);

        return pocket.stream().map(p -> PocketResponse
                .builder()
                .userId(userId)
                .id(p.getId())
                .picture(p.getPicture())
                .pocket_name(p.getName())
                .target(p.getTarget())
                .primary(p.getPrimary())
                .balance(p.getBalance())
                .date(p.getDate())
                .build()).collect(Collectors.toList());


    }

    @Override
    public PocketResponse getDetailPocketByID(String id) {
        Pocket pocket = pocketRepository.findById(id).orElse(null);

        return pocket == null
                ? null : PocketResponse.builder()
                .id(pocket.getId())
                .userId(userInformation.getUserID())
                .picture(pocket.getPicture())
                .pocket_name(pocket.getName())
                .target(pocket.getTarget())
                .primary(pocket.getPrimary())
                .balance(pocket.getBalance())
                .date(pocket.getDate())
                .build();

    }

    @Override
    public PocketResponse createPocket(PocketRequest pocketRequest) {
        User user = userRepository.findById(userInformation.getUserID()).orElse(null);
        String uploadFile = uploadFileUtil.upload(pocketRequest.getPicture());

        Pocket pocket = pocketRepository.save(
                Pocket.builder().name(pocketRequest.getName())
                .picture(uploadFile).target(pocketRequest.getTarget())
                        .primary(false).user(user).date(pocketRequest.getDate())
                .build()
        );


        return PocketResponse.builder()
                .id(pocket.getId())
                .userId(userInformation.getUserID())
                .pocket_name(pocket.getName())
                .picture(uploadFile)
                .target(pocket.getTarget())
                .primary(pocket.getPrimary())
                .balance(0)
                .date(pocket.getDate())
                .build();
    }

    @Override
    public PocketResponse updatePocketById(@PathVariable String id, PocketRequest pocketRequest) {
        Pocket pocket = pocketRepository.findById(id).orElseThrow(()->
                new ApiException(HttpStatus.NOT_FOUND,"Pocket tidak ditemukan"));

        pocket.setName(pocketRequest.getName());
        pocket.setDate(pocketRequest.getDate());
        pocket.setTarget(pocketRequest.getTarget());


        Pocket pocketResult = pocketRepository.save(pocket);

        return PocketResponse.builder()
                .id(pocket.getId())
                .userId(userInformation.getUserID())
                .pocket_name(pocketResult.getName())
                .picture(pocket.getPicture())
                .target(pocketResult.getTarget())
                .primary(pocket.getPrimary())
                .balance(0)
                .date(pocketResult.getDate())
                .build();
    }

    @Override
    public boolean deletePocketById(String id) {
        Pocket pocket = pocketRepository.findById(id).orElseThrow(()->
                new ApiException(HttpStatus.NOT_FOUND,"Pocket tidak ditemukan"));

        pocketRepository.delete(pocket);
        return true;
    }
}
