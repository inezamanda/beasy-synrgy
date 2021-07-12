package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.constant.TransactionConstants;
import com.synrgybootcamp.project.entity.*;
import com.synrgybootcamp.project.enums.PocketAction;
import com.synrgybootcamp.project.enums.RewardPlanetType;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.enums.TransferStatus;
import com.synrgybootcamp.project.helper.GamificationMissionHelper;
import com.synrgybootcamp.project.repository.*;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.TransferService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.web.model.request.TransferRequest;
import com.synrgybootcamp.project.web.model.response.TransferHistoryResponse;
import com.synrgybootcamp.project.web.model.response.TransferResponse;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    TransferRepository transferRepository;

    @Autowired
    PocketRepository pocketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInformation userInformation;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRewardRepository userRewardRepository;

    @Autowired
    GamificationMissionHelper missionHelper;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "user not found"));

        if (!(transferRequest.getPin().equals(user.getPin())))
            throw new ApiException(HttpStatus.NOT_FOUND, "wrong pin");

        Contact contact = contactRepository.findById(transferRequest.getContact_id())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "contact not found"));

        Integer cost = getTransferCost(user, contact);

        if (contact.getBank().getPrimary()) {
            User beneficiary = userRepository.findByAccountNumber(contact.getAccountNumber())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Beneficiary not found"));

            changePocketBalance(
                    pocketRepository.findPrimaryPocket(beneficiary.getId())
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "beneficiary primary pocket not found")),
                    transferRequest.getAmount(), PocketAction.INCREASE
            );
        }

        changePocketBalance(
                pocketRepository.findPrimaryPocket(user.getId())
                        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user primary pocket not found")),
                transferRequest.getAmount() + cost, PocketAction.DECREASE
        );

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        Date date = new Date();
        Transaction transaction = transactionRepository.save(
                Transaction
                        .builder()
                        .description("Transfer Out (" + contact.getBank().getName() + "-" + contact.getAccountNumber() + ")")
                        .user(user)
                        .totalAmount(transferRequest.getAmount() + cost)
                        .type(TransactionType.TRANSFER)
                        .date(date)
                        .build()
        );

        Transfer transfer = transferRepository.save(
                Transfer.builder()
                        .status(TransferStatus.SUCCESS)
                        .cost(cost)
                        .amount(transferRequest.getAmount())
                        .contact(contact)
                        .note(transferRequest.getNote())
                        .user(user)
                        .transaction(transaction)
                        .build()
        );

        if (!contact.getBank().getPrimary()) {
            missionHelper.checkAndValidateTransferMission();
        }

        return TransferResponse
                .builder()
                .status(TransferStatus.SUCCESS.name())
                .bankName(contact.getBank().getName())
                .accountName(contact.getName())
                .beneficiaryAccountNumber(contact.getAccountNumber())
                .amount(transfer.getAmount())
                .message(transfer.getNote())
                .on(transaction.getDate())
                .totalTransfer(transfer.getAmount() + transfer.getCost())
                .refCode(transaction.getId())
                .build();
    }

    @Override
    public List<TransferHistoryResponse> getHistory(Sort sort) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "user not found"));

        return transferRepository.findByUser(user, sort)
                .stream()
                .map(transfer -> TransferHistoryResponse
                        .builder()
                        .accountName(transfer.getContact().getName())
                        .bankName(transfer.getContact().getBank().getName())
                        .beneficiaryAccountNumber(transfer.getContact().getAccountNumber())
                        .amount(transfer.getAmount())
                        .on(transfer.getTransaction().getDate())
                        .build())
                .collect(Collectors.toList());
    }

    private Integer getTransferCost(User user, Contact contact) {
        Integer transferCost = 0;

        if (!contact.getBank().getPrimary()) {
            transferCost = TransactionConstants.DIFFERENT_BANK_FEE;
        }

        List<UserReward> rewards  = userRewardRepository.findByUserIdAndClaimedTrueAndExpiredAtBefore(user.getId(), DateUtils.addMonths(new Date(), 1));

        UserReward reward = rewards.stream()
            .filter(userReward ->
                userReward.getRewardPlanet().getType().equals(RewardPlanetType.TRANSFER))
            .findFirst()
            .orElse(null);

        if (Objects.nonNull(reward)) {
            transferCost = 0;
            reward.setTotalUsed(reward.getTotalUsed() + 1);
            userRewardRepository.save(reward);
        }

        return transferCost;
    }

    private Pocket changePocketBalance(Pocket pocket, Integer amount, PocketAction action) {
        Integer finalBalance = null;
        switch (action) {
            case INCREASE:
                finalBalance = pocket.getBalance() + amount;
                break;
            case DECREASE:
                finalBalance = pocket.getBalance() - amount ;
                if (finalBalance < 0)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "Jumlah melebihi saldo");
                break;
            default:
                break;
        }

        pocket.setBalance(finalBalance);
        return pocketRepository.save(pocket);
    }
}
