package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.DTO.UserDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.service.CourseService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return courseEntity;
    }


}
