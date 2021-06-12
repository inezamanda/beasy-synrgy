package com.synrgybootcamp.project.web.model.response;

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
    String account_number;
    String bank_id;
    String bank_name;
    Integer cost;
}
