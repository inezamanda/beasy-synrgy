package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synrgybootcamp.project.entity.Pocket;
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
    @JsonProperty("user_id")
    String userId;
    String id;
    Integer amount;
    Date date;
//    Pocket destinationPocket;
//    Pocket sourcePocket;
}
