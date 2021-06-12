package com.synrgybootcamp.project.web.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PocketResponse {
    @JsonProperty("user_id")
    String userId;

    String id;
    String picture;
    String pocket_name;
    Integer target;
    Boolean primary;
    Integer balance;
    Date date;

}
