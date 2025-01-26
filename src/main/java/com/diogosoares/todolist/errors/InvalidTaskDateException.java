package com.diogosoares.todolist.errors;

public class InvalidTaskDateException extends RuntimeException {
    public InvalidTaskDateException(String message) {
        super(message);
    }
}