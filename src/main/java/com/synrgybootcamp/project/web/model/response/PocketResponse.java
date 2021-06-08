package com.synrgybootcamp.project.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PocketResponse {
    String id;
    String picture;
    String pocket_name;
    Integer target;
    Boolean primary;
    Integer balance;

}
