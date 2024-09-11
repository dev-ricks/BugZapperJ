package com.github.devricks.bugzapperj.microservice.bug.response;

import org.springframework.http.HttpStatus;

public abstract class BaseResponse implements RestResponse {

    private final String message;

    private int id;

    private HttpStatus status;

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public BaseResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public BaseResponse(String message, int id, HttpStatus status) {
        this.message = message;
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
