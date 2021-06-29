package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.impl.PaymentCreditCardServiceImpl;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.CreditCardPaymentRequest;
import com.synrgybootcamp.project.web.model.request.CreditCardRequest;
import com.synrgybootcamp.project.web.model.response.CreditCardPaymentResponse;
import com.synrgybootcamp.project.web.model.response.CreditCardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentCreditCard {

    @Autowired
    private PaymentCreditCardServiceImpl paymentCreditCardService;

    @PostMapping("creditcard")
    public ResponseEntity<ApiResponse> payCreditCard(@RequestBody CreditCardPaymentRequest creditCardPaymentRequest) {
        CreditCardPaymentResponse creditCardPaymentResponse = paymentCreditCardService.payCreditCard(creditCardPaymentRequest);
        return new ResponseEntity<>(
                new ApiResponse("Successfully pay the Bill", creditCardPaymentResponse)
                , HttpStatus.OK
        );
    }

    @GetMapping("creditcardbill")
    public ResponseEntity<ApiResponse> getCreditCardBill(@RequestBody CreditCardRequest creditCardRequest) {
        CreditCardResponse creditCardResponse = paymentCreditCardService.getCreditCardBill(creditCardRequest);
        return new ResponseEntity<>(
                new ApiResponse("Successfully get the Bill", creditCardResponse)
                , HttpStatus.OK
        );
    }
}
