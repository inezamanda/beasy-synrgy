package com.synrgybootcamp.project.web.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardPaymentRequest {
    @JsonProperty("credit_card_number")
    private String creditCardNumber;

    private Integer amount;
    private Integer pin;
}
