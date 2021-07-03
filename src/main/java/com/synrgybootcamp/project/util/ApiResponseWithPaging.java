package com.synrgybootcamp.project.util;

import lombok.Data;

@Data
public class ApiResponseWithPaging<T> {
  private final Boolean success = true;
  private String message;
  private T data;
  Pagination pagination;

  public ApiResponseWithPaging(String message, T data, Pagination pagination) {
    this.message = message;
    this.data = data;
    this.pagination = pagination;
  }
}
