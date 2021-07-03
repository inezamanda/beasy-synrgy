package com.synrgybootcamp.project.util;

import lombok.Data;

@Data
public class ApiResponseWithoutData {
  private final Boolean success = true;
  private String message;

  public ApiResponseWithoutData(String message) {
    this.message = message;
  }
}
