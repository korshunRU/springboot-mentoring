package ru.korshun.solbeg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.korshun.solbeg.config.exception.DataNotFoundException;
import ru.korshun.solbeg.config.exception.UserExistException;
import ru.korshun.solbeg.utils.BaseResponse;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionControlle extends ResponseEntityExceptionHandler {

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<BaseResponse<Void>> authError(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage()));
  }

  @ExceptionHandler({DataNotFoundException.class})
  public ResponseEntity<BaseResponse<Void>> dataNotFoundError(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new BaseResponse<>(HttpStatus.NOT_FOUND, e.getMessage()));
  }

  @ExceptionHandler({UserExistException.class})
  public ResponseEntity<BaseResponse<Void>> authNotFoundError(Exception e) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new BaseResponse<>(HttpStatus.CONFLICT, e.getMessage()));
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<BaseResponse<Void>> authValidateError(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage()));
  }

}
