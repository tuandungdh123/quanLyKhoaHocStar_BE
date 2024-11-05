package com.example.coursemanagement.exception;

public enum ErrorCode {
    STUDENT_EXISTED(1001, "Student existed"),
    STUDENT_NOT_EXIST(1002, "Student not exist"),
    USER_NOT_EXISTED(1003, "User not exist"),
    UNAUTHENTICATED(1004, "Unauthenticated");

    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static final String COURSE_NOT_FOUND = "COURSE_NOT_FOUND";
    public static final String PASSWORDS_DO_NOT_MATCH = "PASSWORDS_DO_NOT_MATCH";
    public static final String MODULE_NOT_FOUND = "MODULE_NOT_FOUND";
    public static final String LESSON_NOT_FOUND = "LESSON_NOT_FOUND";
    public static final String CHOICE_NOT_FOUND = "COURSE_NOT_FOUND";
    public static final String QUESTION_NOT_FOUND = "QUESTION_NOT_FOUND";
    public static final String MEET_SCHEDULE_NOT_FOUND = "MEET_SCHEDULE_NOT_FOUND";
    public static final String INVALID_INSTRUCTOR_ROLE = "INVALID_INSTRUCTOR_ROLE";
    public static final String INVALID_INSTRUCTOR = "INVALID_INSTRUCTOR";

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
