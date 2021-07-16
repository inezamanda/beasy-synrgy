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
public class TransferHistoryResponse {
    @JsonProperty("bank_name")
    String bankName;

    @JsonProperty("account_name")
    String accountName;

    @JsonProperty("beneficiary_account_number")
    String beneficiaryAccountNumber;

    Integer amount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    Date on;
}
