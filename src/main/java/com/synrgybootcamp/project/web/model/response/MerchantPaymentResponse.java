package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPaymentResponse {
    String id;
    String name;
    Integer amount;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    Date on;
}
