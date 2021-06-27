package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.PaymentMobile;
import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.MobileType;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.PaymentMobileRepository;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.TransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PaymentMobileService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.PhoneNumberChecker;
import com.synrgybootcamp.project.web.model.request.MobilePaymentRequest;
import com.synrgybootcamp.project.web.model.request.MobileRequest;
import com.synrgybootcamp.project.web.model.response.MobileCreditResponse;
import com.synrgybootcamp.project.web.model.response.MobileDataResponse;
import com.synrgybootcamp.project.web.model.response.MobilePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class PaymentMobileServiceImpl implements PaymentMobileService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInformation userInformation;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private PaymentMobileRepository paymentMobileRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PhoneNumberChecker phoneNumberChecker;

    @Override
    public MobilePaymentResponse payMobile(MobilePaymentRequest mobilePaymentRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        Pocket pocket = pocketRepository.findPrimaryPocket(user.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Primary pocket not found"));

        PaymentMobile paymentMobile = paymentMobileRepository.findById(mobilePaymentRequest.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Mobile credit/data not found"));

        if(!(mobilePaymentRequest.getPin().equals(user.getPin())))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Wrong pin");

        Integer finalBalance = pocket.getBalance() - paymentMobile.getPrice();
        if (finalBalance < 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Jumlah melebihi saldo");

        pocket.setBalance(finalBalance);
        pocketRepository.save(pocket);

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
        Date date = new Date();
        Transaction transaction = transactionRepository.save(
                Transaction
                        .builder()
                        .user(user)
                        .description(paymentMobile.getDescription())
                        .type(TransactionType.PAYMENT_MOBILE)
                        .totalAmount(paymentMobile.getPrice())
                        .date(date)
                        .build()
        );

        return MobilePaymentResponse
                .builder()
                .id(transaction.getId())
                .name(paymentMobile.getName())
                .description(transaction.getDescription())
                .amount(transaction.getTotalAmount())
                .phoneNumber(mobilePaymentRequest.getPhoneNumber())
                .on(transaction.getDate())
                .build();
    }

    @Override
    public List<MobileCreditResponse> getDenomList(MobileRequest mobileRequest) {
        List<PaymentMobile> denoms = paymentMobileRepository
                .findByMobileOperatorAndMobileType(phoneNumberChecker
                        .numberChecker(mobileRequest.getPhoneNumber()), MobileType.CREDIT);

        return denoms.stream()
                .map(denom -> MobileCreditResponse
                        .builder()
                        .id(denom.getId())
                        .denom(denom.getName())
                        .price(denom.getPrice())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<MobileDataResponse> getDataList(MobileRequest mobileRequest) {
        List<PaymentMobile> datas = paymentMobileRepository
                .findByMobileOperatorAndMobileType(phoneNumberChecker
                        .numberChecker(mobileRequest.getPhoneNumber()), MobileType.DATA);

        return datas.stream()
                .map(data -> MobileDataResponse
                        .builder()
                        .id(data.getId())
                        .name(data.getName())
                        .description(data.getDescription())
                        .price(data.getPrice())
                        .build()
                ).collect(Collectors.toList());
    }
}
