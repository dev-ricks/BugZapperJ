package com.github.devricks.bugzapperj.microservice.bug.exception;

public class NullBugDataException extends RuntimeException {
    public NullBugDataException(String message) {
        super(message);
    }
}
