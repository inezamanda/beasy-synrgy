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
public class ContactResponse {
    String id;
    String name;
    @JsonProperty("account_number")
    String accountNumber;
    @JsonProperty("bank_id")
    String bankId;
    @JsonProperty("bank_name")
    String bankName;
    Integer cost;
}
