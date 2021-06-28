package com.synrgybootcamp.project.service.impl;

import com.synrgybootcamp.project.entity.PaymentMerchant;
import com.synrgybootcamp.project.entity.Pocket;
import com.synrgybootcamp.project.entity.Transaction;
import com.synrgybootcamp.project.entity.User;
import com.synrgybootcamp.project.enums.TransactionType;
import com.synrgybootcamp.project.repository.PaymentMerchantRepository;
import com.synrgybootcamp.project.repository.PocketRepository;
import com.synrgybootcamp.project.repository.TransactionRepository;
import com.synrgybootcamp.project.repository.UserRepository;
import com.synrgybootcamp.project.security.utility.UserInformation;
import com.synrgybootcamp.project.service.PaymentMerchantService;
import com.synrgybootcamp.project.util.ApiException;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MerchantPaymentRequest;
import com.synrgybootcamp.project.web.model.request.MerchantRequest;
import com.synrgybootcamp.project.web.model.response.MerchantPaymentResponse;
import com.synrgybootcamp.project.web.model.response.MerchantResponse;
import com.synrgybootcamp.project.web.model.response.MobilePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class PaymentMerchantServiceImpl implements PaymentMerchantService {
    @Autowired
    private PaymentMerchantRepository merchantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserInformation userInformation;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    UploadFileUtil uploadFileUtil;

    @Override
    public MerchantResponse addMerchant(MerchantRequest merchantRequest) {
        String uploadFile = uploadFileUtil.upload(merchantRequest.getLogo());

        PaymentMerchant merchant = merchantRepository.save(
                PaymentMerchant.builder()
                    .name(merchantRequest.getName())
                    .logo(uploadFile)
                    .build()
        );

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .logo(uploadFile)
                .build();
    }

    @Override
    public List<MerchantResponse> getAllMerchants() {
        List<PaymentMerchant> merchants = merchantRepository.findAll();

        return merchants
                .stream()
                .map(merchant -> MerchantResponse
                        .builder()
                    .id(merchant.getId())
                    .name(merchant.getName())
                    .logo(merchant.getLogo())
                    .build()
                ).collect(Collectors.toList());
    }

    @Override
    public MerchantResponse getMerchantById(String id) {
        PaymentMerchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "merchant is not found"));

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .logo(merchant.getLogo())
                .build();
    }

    @Override
    public MerchantResponse updateMerchantById(String id, MerchantRequest merchantRequest) {
        PaymentMerchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "merchant not found"));
        String uploadFile = uploadFileUtil.upload(merchantRequest.getLogo());

        merchant.setName(merchantRequest.getName());
        merchant.setLogo(uploadFile);

        PaymentMerchant merchantResult = merchantRepository.save(merchant);

        return MerchantResponse
                .builder()
                .id(merchantResult.getId())
                .name(merchantResult.getName())
                .logo(merchantResult.getLogo())
                .build();
    }

    @Override
    public boolean deleteMerchantById(String id) {
        PaymentMerchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "merchant is not found"));

        merchantRepository.delete(merchant);

        return true;
    }

    @Override
    public MerchantPaymentResponse payMerchant(MerchantPaymentRequest merchantPaymentRequest) {
        User user = userRepository.findById(userInformation.getUserID())
                .orElseThrow(()-> new ApiException(HttpStatus.NOT_FOUND, "user not found"));

        Pocket pocket = pocketRepository.findPrimaryPocket(user.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user primary pocket not found"));

        PaymentMerchant merchant = merchantRepository.findById(merchantPaymentRequest.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "merchant not found"));

        if (!merchantPaymentRequest.getPin().equals(user.getPin()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "wrong pin");

        Integer finalBalance = pocket.getBalance() - merchantPaymentRequest.getAmount();

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
                        .description(merchant.getName())
                        .type(TransactionType.PAYMENT_MERCHANT)
                        .totalAmount(merchantPaymentRequest.getAmount())
                        .date(date)
                        .build()
        );

        return MerchantPaymentResponse
                .builder()
                .id(transaction.getId())
                .name(transaction.getDescription())
                .amount(transaction.getTotalAmount())
                .on(transaction.getDate())
                .build();
    }
}
