package com.github.devricks.bugzapperj.microservice.bug.response;

import org.springframework.http.HttpStatus;

public class NoContentResponse extends BaseResponse {

    public NoContentResponse(String message) {
        super(message, HttpStatus.NO_CONTENT);
    }

    public NoContentResponse(String message, int id) {
        super(message, id, HttpStatus.NO_CONTENT);
    }
}
