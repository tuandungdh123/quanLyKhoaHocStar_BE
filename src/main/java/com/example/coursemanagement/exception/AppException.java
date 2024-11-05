package com.example.coursemanagement.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final int errorCode;

    public AppException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

}
