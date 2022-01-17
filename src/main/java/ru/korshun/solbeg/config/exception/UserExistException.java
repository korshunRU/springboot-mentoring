package ru.korshun.solbeg.config.exception;

public class UserExistException extends RuntimeException {
  public UserExistException(String message) {
    super(message);
  }
}
