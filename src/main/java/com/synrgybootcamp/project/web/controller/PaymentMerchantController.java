package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PaymentMerchantServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.util.UploadFileUtil;
import com.synrgybootcamp.project.web.model.request.MerchantPaymentRequest;
import com.synrgybootcamp.project.web.model.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment/merchant")
@Api(tags = "Payment (Merchant)", description = "Payment Controller for Merchant")
public class PaymentMerchantController {

    @Autowired
    private PaymentMerchantServiceImpl paymentMerchantService;

    @PostMapping("")
    @ApiOperation("Pay merchant bill")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<MerchantPaymentResponse> payMerchant(@RequestBody MerchantPaymentRequest merchantPaymentRequest) {
        MerchantPaymentResponse merchantPaymentResponse = paymentMerchantService.payMerchant(merchantPaymentRequest);

        return new ApiResponse<>("Transaction Success", merchantPaymentResponse);
    }

    @GetMapping("")
    @ApiOperation("Get list of merchants")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<List<MerchantResponse>> getMerchantList() {
        List<MerchantResponse> merchantResponses = paymentMerchantService.getAllMerchants();

        return new ApiResponse<>("Successfully get merchant list", merchantResponses);
    }
}
