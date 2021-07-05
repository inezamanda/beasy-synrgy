package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MerchantPaymentRequest;
import com.synrgybootcamp.project.web.model.request.MerchantRequest;
import com.synrgybootcamp.project.web.model.response.MerchantPaymentResponse;
import com.synrgybootcamp.project.web.model.response.MerchantResponse;

import java.util.List;

public interface PaymentMerchantService {
    MerchantResponse addMerchant(MerchantRequest merchantRequest);
    List<MerchantResponse> getAllMerchants();
    MerchantResponse getMerchantById(String id);
    MerchantResponse updateMerchantById(String id, MerchantRequest merchantRequest);
    boolean deleteMerchantById(String id);
    MerchantPaymentResponse payMerchant(MerchantPaymentRequest merchantPaymentRequest);
}
