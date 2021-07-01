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
public class AccountRequest {
    String name;
    @JsonProperty("ewallet_id")
    String ewalletId;
    @JsonProperty("account_number")
    String accountNumber;
}
