package com.synrgybootcamp.project.web.model.response;

import com.synrgybootcamp.project.web.model.response.sub.SubCardInfoResponse;
import com.synrgybootcamp.project.web.model.response.sub.SubRecentTransactionResponse;
import com.synrgybootcamp.project.web.model.response.sub.SubWebGraphResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebDashboardResponse {

  Integer currentBalance;
  SubCardInfoResponse cardInformation;
  SubRecentTransactionResponse recentTransaction;
  SubWebGraphResponse graph;

}
