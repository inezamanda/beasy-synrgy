package com.synrgybootcamp.project.web.model.response.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCardInfoResponse {

  Integer balance;

  String cardHolderName;

  Integer lastFourDigit;

  String expiredAt;

}
