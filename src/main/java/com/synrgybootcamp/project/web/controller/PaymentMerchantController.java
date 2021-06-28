package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PaymentMerchantServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MerchantPaymentRequest;
import com.synrgybootcamp.project.web.model.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment/merchant")
public class PaymentMerchantController {

    @Autowired
    private PaymentMerchantServiceImpl paymentMerchantService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> payMerchant(@RequestBody MerchantPaymentRequest merchantPaymentRequest) {
        MerchantPaymentResponse merchantPaymentResponse = paymentMerchantService.payMerchant(merchantPaymentRequest);

        return new ResponseEntity<>(
                new ApiResponse("Transaction Success", merchantPaymentResponse)
                , HttpStatus.OK
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getMerchantList() {
        List<MerchantResponse> merchantResponses = paymentMerchantService.getAllMerchants();

        return new ResponseEntity<>(
                new ApiResponse("Successfully get merchant list", merchantResponses)
                , HttpStatus.OK
        );
    }
}
