package com.synrgybootcamp.project.web.model.response.sub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubWebGraphResponse {

  List<String> label;

  List<Integer> data;

  Integer total;

}
