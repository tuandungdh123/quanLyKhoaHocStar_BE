package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.exception.AppException;

import java.sql.SQLException;
import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourse();

    CourseDTO updateCourse(CourseDTO courseDTO) throws SQLException, AppException;

    CourseDTO doSaveCourse(CourseDTO courseDTO) throws SQLException, AppException;

    void deleteCourseById(Integer courseId) throws SQLException, AppException;
}
