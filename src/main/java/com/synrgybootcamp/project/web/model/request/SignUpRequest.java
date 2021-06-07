package com.synrgybootcamp.project.web.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String telephone;
    private String password;
    private String accountNumber;
    private Integer balance;
    private String fullName;
    private Integer pin;
    private String[] roles;
}
