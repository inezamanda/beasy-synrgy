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
public class MovePocketBalanceResponse {
    @JsonProperty("pocket_source_id")
    String pocketSourceId;

    @JsonProperty("pocket_source_name")
    String pocketSourceName;

    @JsonProperty("pocket_source_balance")
    Integer pocketSourceBalance;

    @JsonProperty("pocket_destination_id")
    String pocketDestinationId;

    @JsonProperty("pocket_destination_name")
    String pocketDestinationName;

    @JsonProperty("pocket_destination_balance")
    Integer pocketDestinationBalance;

    Integer amount;
}
