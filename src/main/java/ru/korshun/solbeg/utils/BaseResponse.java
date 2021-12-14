package ru.korshun.solbeg.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
  private final int code;
  private final String message;
  private final T data;

  public BaseResponse(T data) {
    this.code = HttpStatus.OK.value();
    this.message = null;
    this.data = data;
  }

  public BaseResponse(HttpStatus code, String message) {
    this.code = code.value();
    this.message = message;
    this.data = null;
  }

  public BaseResponse(HttpStatus code) {
    this.code = code.value();
    this.message = null;
    this.data = null;
  }
}
