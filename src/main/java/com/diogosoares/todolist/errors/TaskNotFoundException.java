package com.diogosoares.todolist.errors;

public class TaskNotFoundException extends RuntimeException {
  public TaskNotFoundException(String message) {
      super(message);
  }
}