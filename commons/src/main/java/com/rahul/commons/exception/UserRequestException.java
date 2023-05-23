package com.rahul.commons.exception;

import org.springframework.http.HttpStatus;

public class UserRequestException extends GenricException {

    public UserRequestException(String message) {
        super(message);
    }

    public UserRequestException(String message, HttpStatus status) {
        super(message, status);
    }

    public UserRequestException(String message, Throwable e) {
        super(message, e);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
