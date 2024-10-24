package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.DTO.UserDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourse();
}
