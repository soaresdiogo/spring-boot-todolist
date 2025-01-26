package com.diogosoares.todolist.errors;

public class UnauthorizedTaskUpdateException extends RuntimeException {
  public UnauthorizedTaskUpdateException(String message) {
      super(message);
  }
}
