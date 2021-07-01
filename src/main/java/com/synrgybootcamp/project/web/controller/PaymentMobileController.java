package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PaymentMobileServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.MobilePaymentRequest;
import com.synrgybootcamp.project.web.model.request.MobileRequest;
import com.synrgybootcamp.project.web.model.response.MobileCreditResponse;
import com.synrgybootcamp.project.web.model.response.MobileDataResponse;
import com.synrgybootcamp.project.web.model.response.MobilePaymentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment")
@Api(tags = "Payment (Pulsa and Data)", description = "Payment Controller for Mobile Credit and Data")
public class PaymentMobileController {

    @Autowired
    private PaymentMobileServiceImpl mobileService;

    @PostMapping("/mobile")
    @ApiOperation(value = "Pay mobile credit or data")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> payMobile(@RequestBody MobilePaymentRequest mobilePaymentRequest) {
        MobilePaymentResponse mobilePaymentResponse = mobileService.payMobile(mobilePaymentRequest);
        return new ResponseEntity<>(
                new ApiResponse("Transaction Success", mobilePaymentResponse)
                , HttpStatus.OK
        );
    }

    @GetMapping("/mobilecredit/{phoneNumber}")
    @ApiOperation(value = "Get list of mobile credit")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getMobileCreditList(@PathVariable String phoneNumber) {
        List<MobileCreditResponse> denom = mobileService.getDenomList(phoneNumber);
        return new ResponseEntity<>(
                new ApiResponse("Successfully get mobile credit list", denom)
                , HttpStatus.OK
        );
    }

    @GetMapping("/mobiledata/{phoneNumber}")
    @ApiOperation(value = "Get list of mobile data")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> getMobileDataList(@PathVariable String phoneNumber) {
        List<MobileDataResponse> data = mobileService.getDataList(phoneNumber);
        return new ResponseEntity<>(
                new ApiResponse("Successfully get mobile data list", data)
                , HttpStatus.OK
        );
    }
}
