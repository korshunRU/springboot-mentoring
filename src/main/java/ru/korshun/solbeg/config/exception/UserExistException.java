package ru.korshun.solbeg.config.exception;

public class UserExistException extends Exception {
  public UserExistException(String message) {
    super(message);
  }
}
