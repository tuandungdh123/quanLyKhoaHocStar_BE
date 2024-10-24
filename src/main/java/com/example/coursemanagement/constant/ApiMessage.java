package com.example.coursemanagement.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ApiMessage {
    @Getter
    @AllArgsConstructor
    public enum BasicMessageApi {
        SUCCESS("Success"),
        FAIL("Fail");

        private final String basicMessageApi;
    }
}