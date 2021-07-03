package com.synrgybootcamp.project.web.controller;

import com.synrgybootcamp.project.service.PaymentCreditCardService;
import com.synrgybootcamp.project.util.ApiResponse;
import com.synrgybootcamp.project.web.model.request.CreditCardPaymentRequest;
import com.synrgybootcamp.project.web.model.response.CreditCardPaymentResponse;
import com.synrgybootcamp.project.web.model.response.CreditCardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@Api(tags = "Payment (Credit Card)", description = "Payment Controller for Credit Card")
public class PaymentCreditCard {

    @Autowired
    private PaymentCreditCardService paymentCreditCardService;

    @PostMapping("creditcard")
    @ApiOperation(value = "Pay credit card bill")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<CreditCardPaymentResponse> payCreditCard(@RequestBody CreditCardPaymentRequest creditCardPaymentRequest) {
        CreditCardPaymentResponse creditCardPaymentResponse = paymentCreditCardService.payCreditCard(creditCardPaymentRequest);

        return new ApiResponse<>("Successfully pay the Bill", creditCardPaymentResponse);
    }

    @GetMapping("creditcardbill/{creditCardNumber}")
    @ApiOperation(value = "Get the Credit Card Bill")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ApiResponse<CreditCardResponse> getCreditCardBill(@PathVariable String creditCardNumber) {
        CreditCardResponse creditCardResponse = paymentCreditCardService.getCreditCardBill(creditCardNumber);

        return new ApiResponse<>("Successfully get the Bill", creditCardResponse);
    }
}
