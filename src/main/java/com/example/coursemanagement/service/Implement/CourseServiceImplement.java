package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImplement implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<CourseDTO> getAllCourse(){
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Integer courseId) throws AppException {
        var courseEntityOptional = courseRepository.findByCourseId(courseId);
        if (courseEntityOptional.isEmpty()) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND, "Course not found");
        }
        return convertToDto(courseEntityOptional.get());
    }

    @Override
    public CourseDTO doSaveCourse(CourseDTO courseDTO) throws SQLException, AppException {
        CourseEntity courseEntity = convertToEntity(courseDTO);
        courseEntity = courseRepository.save(courseEntity);
        return convertToDto(courseEntity);
    }

    @Override
    public void deleteCourseById(Integer courseId) throws SQLException, AppException {
        var courseEntityOptional = courseRepository.findById(courseId);
        if (courseEntityOptional.isEmpty()) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND, "Course not found");
        }

        courseRepository.deleteById(courseId);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) throws SQLException, AppException {
        var courseEntityOptional = courseRepository.findById(courseDTO.getCourseId());
        if (courseEntityOptional.isEmpty()) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND, "Course Not Found");
        }



        CourseEntity existingCourse = courseEntityOptional.get();

        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setImgUrl(courseDTO.getImgUrl());
        existingCourse.setStartDate(courseDTO.getStartDate());
        existingCourse.setEndDate(courseDTO.getEndDate());
        existingCourse.setMeetingTime(courseDTO.getMeetingTime());
        existingCourse.setSchedule(courseDTO.getSchedule());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setStatus(courseDTO.getStatus());
        existingCourse.setInstructor(courseDTO.getInstructor());

        CourseEntity updatedCourse = courseRepository.save(existingCourse);

        return convertToDto(updatedCourse);
    }

    private CourseDTO convertToDto(CourseEntity courseEntity) {
        return CourseDTO.builder()
                .courseId(courseEntity.getCourseId())
                .title(courseEntity.getTitle())
                .description(courseEntity.getDescription())
                .imgUrl(courseEntity.getImgUrl())
                .startDate(courseEntity.getStartDate())
                .endDate(courseEntity.getEndDate())
                .meetingTime(courseEntity.getMeetingTime())
                .schedule(courseEntity.getSchedule())
                .price(courseEntity.getPrice())
                .status(courseEntity.getStatus())
                .createdAt(courseEntity.getCreatedAt())
                .instructor(courseEntity.getInstructor())
                .build();
    }

    private CourseEntity convertToEntity(CourseDTO courseDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseId(courseDTO.getCourseId());
        courseEntity.setTitle(courseDTO.getTitle());
        courseEntity.setDescription(courseDTO.getDescription());
        courseEntity.setImgUrl(courseDTO.getImgUrl());
        courseEntity.setStartDate(courseDTO.getStartDate());
        courseEntity.setEndDate(courseDTO.getEndDate());
        courseEntity.setMeetingTime(courseDTO.getMeetingTime());
        courseEntity.setSchedule(courseDTO.getSchedule());
        courseEntity.setPrice(courseDTO.getPrice());
        courseEntity.setStatus(courseDTO.getStatus());
        courseEntity.setInstructor(courseDTO.getInstructor());

        return courseEntity;
    }


}
