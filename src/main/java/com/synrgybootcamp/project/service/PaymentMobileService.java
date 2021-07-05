package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.MobilePaymentRequest;
import com.synrgybootcamp.project.web.model.request.MobileRequest;
import com.synrgybootcamp.project.web.model.response.MobileCreditResponse;
import com.synrgybootcamp.project.web.model.response.MobileDataResponse;
import com.synrgybootcamp.project.web.model.response.MobilePaymentResponse;

import java.util.List;

public interface PaymentMobileService {
    MobilePaymentResponse payMobile(MobilePaymentRequest mobilePaymentRequest);
    List<MobileCreditResponse> getDenomList(String phoneNumber);
    List<MobileDataResponse> getDataList(String phoneNumber);
}
