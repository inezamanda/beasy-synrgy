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
public class MobilePaymentResponse {
    private String id;
    private String name;
    private String description;
    private Integer amount;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+7")
    private Date on;
}
