package com.example.coursemanagement.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    STUDENT_EXISTED(1001, "Học viên đã tồn tại"),
    STUDENT_NOT_EXIST(1002, "Học viên không tồn tại"),
    USER_NOT_EXISTED(1003, "Người dùng không tồn tại"),
    UNAUTHENTICATED(1004, "Chưa xác thực"),
    PROGRESS_NOT_FOUND(1005, "Không tìm thấy tiến trình nào"),
    USER_NOT_FOUND(2001, "Không tìm thấy người dùng nào"),
    INVALID_CREDENTIALS(2002, "Thông tin đăng nhập không hợp lệ"),
    USER_ALREADY_EXISTS(2003, "Người dùng đã tồn tại"),
    COURSE_NOT_FOUND(2004, "Không tìm thấy khóa học nào"),
    PASSWORDS_DO_NOT_MATCH(2005, "Mật khẩu không khớp"),
    MODULE_NOT_FOUND(2006, "Không tìm thấy chương học nào"),
    LESSON_NOT_FOUND(2007, "Không tìm thấy bài học nào"),
    CHOICE_NOT_FOUND(2008, "Không tìm thấy lựa chọn nào"),
    QUESTION_NOT_FOUND(2009, "Không tìm thấy câu hỏi nào"),
    MEET_SCHEDULE_NOT_FOUND(2010, "Không tìm thấy lịch họp nào"),
    ENROLLMENT_NOT_FOUND(2011, "Không tìm thấy đăng ký nào"),
    PAYMENT_NOT_FOUND(2012, "Không tìm thấy thông tin thanh toán nào"),
    QUIZ_NOT_FOUND(2013, "Không tìm thấy quiz nào"),
    INVALID_INSTRUCTOR(2014,"Giảng viên này không tồn tại" ),
    INVALID_INSTRUCTOR_ROLE(2015,"Bạn không phải là giảng viên" );


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
