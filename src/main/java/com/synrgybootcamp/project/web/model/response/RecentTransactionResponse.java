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
public class RecentTransactionResponse {
    private String id;

    @JsonProperty("transaction_type")
    private String transactionType;

    private String description;
    private Integer amount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date on;
}
