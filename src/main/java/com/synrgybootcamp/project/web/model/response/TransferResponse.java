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
public class TransferResponse {
    String status;

    @JsonProperty("bank_name")
    String bankName;

    @JsonProperty("beneficiary_account_number")
    String beneficiaryAccountNumber;

    Integer amount;
    String message;

    @JsonProperty("total_transfer")
    Integer totalTransfer;

    @JsonFormat(pattern = "dd-MM-yyyy")
    Date on;
}
