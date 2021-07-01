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
public class EwalletTransactionResponse {
    String status;

    @JsonProperty("ewallet_name")
    String ewalletName;

    @JsonProperty("account_name")
    String accountName;

    @JsonProperty("account_number")
    String accountNumber;

    Integer amount;
    String message;

    @JsonProperty("total_transfer")
    Integer totalTransfer;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    Date on;

    String refCode;
}
