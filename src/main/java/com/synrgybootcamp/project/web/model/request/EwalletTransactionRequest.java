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
public class EwalletTransactionRequest {
    @JsonProperty("account_id")
    String accountId;
    Integer amount;
    String message;
    Integer pin;
}
