package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.synrgybootcamp.project.enums.PocketBalanceStatus;
import com.synrgybootcamp.project.enums.PocketTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PocketTransactionResponse {
    Integer amount;
    PocketTransactionType pocketTransactionType;
    PocketBalanceStatus pocketBalanceStatus;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    Date date;
}
