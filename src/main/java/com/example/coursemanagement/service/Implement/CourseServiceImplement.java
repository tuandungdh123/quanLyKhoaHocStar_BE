package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.CourseDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.UserEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public List<CourseDTO> getAllCourse(){
        List<CourseEntity> courses = courseRepository.findAll();
        return courses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Integer courseId) throws AppException {
        return courseRepository.findByCourseId(courseId)
                .map(this::convertToDto)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học với ID: " + courseId));
    }

    @Override
    public CourseDTO doSaveCourse(CourseDTO courseDTO) throws SQLException, AppException {
        UserEntity instructor = null;
        if (courseDTO.getInstructor() != null) {
            instructor = userRepository.findById(courseDTO.getInstructor())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_INSTRUCTOR,
                            "Giảng viên này không tồn tại"));

            if (instructor.getRole().getRoleId() != 2) {
                throw new AppException(ErrorCode.INVALID_INSTRUCTOR, "Người dùng không phải là giảng viên hợp lệ");
            }
        }

        CourseEntity courseEntity;

        if (courseDTO.getCourseId() != null) {
            courseEntity = courseRepository.findById(courseDTO.getCourseId())
                    .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND,
                            "Không tìm thấy khóa học với ID: " + courseDTO.getCourseId()));
        } else {
            courseEntity = new CourseEntity();
        }

        courseEntity.setTitle(courseDTO.getTitle());
        courseEntity.setDescription(courseDTO.getDescription());
        courseEntity.setImgUrl(courseDTO.getImgUrl());
        courseEntity.setStartDate(courseDTO.getStartDate());
        courseEntity.setEndDate(courseDTO.getEndDate());
        courseEntity.setMeetingTime(courseDTO.getMeetingTime());
        courseEntity.setSchedule(courseDTO.getSchedule());
        courseEntity.setPrice(courseDTO.getPrice());
        courseEntity.setStatus(courseDTO.getStatus());
        courseEntity.setInstructor(instructor);

        CourseEntity savedCourse = courseRepository.save(courseEntity);

        return convertToDto(savedCourse);
    }



    @Override
    public void deleteCourseById(Integer courseId) throws SQLException, AppException {
        if (!courseRepository.existsById(courseId)) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học với ID: " + courseId);
        }
        courseRepository.deleteById(courseId);
    }
    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) throws SQLException, AppException {
        // Kiểm tra xem courseId có tồn tại không
        CourseEntity existingCourse = courseRepository.findById(courseDTO.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND,
                        "Không tìm thấy khóa học với ID: " + courseDTO.getCourseId()));

        // Nếu instructor không null, kiểm tra giảng viên hợp lệ
        if (courseDTO.getInstructor() != null) {
            UserEntity instructor = userRepository.findById(courseDTO.getInstructor())
                    .orElseThrow(() -> new AppException(ErrorCode.INVALID_INSTRUCTOR,
                            "Giảng viên này không tồn tại"));

            // Kiểm tra role của giảng viên
            if (instructor.getRole().getRoleId() != 2) {
                throw new AppException(ErrorCode.INVALID_INSTRUCTOR, "Người dùng không phải là giảng viên hợp lệ");
            }

            // Cập nhật instructor
            existingCourse.setInstructor(instructor);
        }

        // Cập nhật các trường khác
        existingCourse.setTitle(courseDTO.getTitle());
        existingCourse.setDescription(courseDTO.getDescription());
        existingCourse.setImgUrl(courseDTO.getImgUrl());
        existingCourse.setStartDate(courseDTO.getStartDate());
        existingCourse.setEndDate(courseDTO.getEndDate());
        existingCourse.setMeetingTime(courseDTO.getMeetingTime());
        existingCourse.setSchedule(courseDTO.getSchedule());
        existingCourse.setPrice(courseDTO.getPrice());
        existingCourse.setStatus(courseDTO.getStatus());

        // Lưu thông tin khóa học đã cập nhật
        CourseEntity updatedCourse = courseRepository.save(existingCourse);

        // Chuyển đổi sang DTO và trả về
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
                .instructor(courseEntity.getInstructor() != null ? courseEntity.getInstructor().getUserId() : null)
                .build();
    }

    private CourseEntity convertToEntity(CourseDTO courseDTO) throws AppException {
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

        if (courseDTO.getInstructor() != null) {
            UserEntity instructor = new UserEntity();
            instructor.setUserId(courseDTO.getInstructor());
            courseEntity.setInstructor(instructor);
        }

        return courseEntity;
    }

    @Override
    public List<CourseDTO> getCourseByInstructorId(Integer instructorId) throws AppException {
        // Kiểm tra giảng viên có tồn tại không
        UserEntity instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND, "Không tìm thấy giảng viên với ID: " + instructorId));

        // Kiểm tra vai trò của người dùng
        if (instructor.getRole().getRoleId() != 2) {
            throw new AppException(ErrorCode.INVALID_INSTRUCTOR, "Người dùng không phải là giảng viên hợp lệ");
        }

        // Lấy danh sách khóa học dựa trên instructor
        List<CourseEntity> courses = courseRepository.findByInstructor(instructor);

        if (courses.isEmpty()) {
            throw new AppException(ErrorCode.COURSE_NOT_FOUND, "Giảng viên này chưa tạo khóa học nào");
        }

        // Chuyển đổi danh sách sang DTO
        return courses.stream().map(this::convertToDto).collect(Collectors.toList());
    }

}
