package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardResponse {
    private String name;

    @JsonProperty("credit_card_number")
    private String creditcardnumber;

    private String bank;

    @JsonProperty("bill_payment")
    private Integer billpayment;

    @JsonProperty("minimum_payment")
    private Integer minimumpayment;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    private Date on;
}
