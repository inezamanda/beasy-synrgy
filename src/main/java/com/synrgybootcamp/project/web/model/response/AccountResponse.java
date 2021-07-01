package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    String id;
    String name;

    @JsonProperty("account_number")
    String accountNumber;

    @JsonProperty("ewallet_id")
    String ewalletId;

    @JsonProperty("ewallet_name")
    String ewalletName;

    @JsonProperty("adminFee")
    Integer adminFee;
}
