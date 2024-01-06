package com.taskmaster.manager.exception;

public class WorklogNotFoundException extends RuntimeException {
    public WorklogNotFoundException(String msg) {
        super(msg);
    }
}