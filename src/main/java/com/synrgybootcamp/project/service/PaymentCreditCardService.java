package com.synrgybootcamp.project.service;

import com.synrgybootcamp.project.web.model.request.CreditCardPaymentRequest;
import com.synrgybootcamp.project.web.model.request.CreditCardRequest;
import com.synrgybootcamp.project.web.model.response.CreditCardPaymentResponse;
import com.synrgybootcamp.project.web.model.response.CreditCardResponse;

public interface PaymentCreditCardService {
    CreditCardResponse getCreditCardBill(String creditCardNumber);
    CreditCardPaymentResponse payCreditCard(CreditCardPaymentRequest creditCardPaymentRequest);
}
