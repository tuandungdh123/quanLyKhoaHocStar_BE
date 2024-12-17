package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.MeetingScheduleDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.MeetingScheduleEntity;
import com.example.coursemanagement.exception.AppException;
import com.example.coursemanagement.exception.ErrorCode;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.MeetingScheduleRepository;
import com.example.coursemanagement.service.MeetingScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetScheduleServiceImplement implements MeetingScheduleService {
    private final MeetingScheduleRepository meetingScheduleRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<MeetingScheduleDTO> getAllMeetingSchedules() {
        return meetingScheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingScheduleDTO getMeetingScheduleById(Integer meetingId) {
        return meetingScheduleRepository.findById(meetingId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new AppException(ErrorCode.MEET_SCHEDULE_NOT_FOUND, "Không tìm thấy lịch họp nào."));
    }

    @Override
    public MeetingScheduleDTO createMeetingSchedule(MeetingScheduleDTO dto) {
        System.out.println("urlMeeting: " + dto.getUrlMeeting());
        // Kiểm tra nếu URL không hợp lệ
        if (dto.getUrlMeeting() == null || dto.getUrlMeeting().trim().isEmpty()) {
            throw new IllegalArgumentException("URL Meeting không được để trống!");
        }

        // Validate URL hợp lệ (nếu cần thiết)
        try {
            new URL(dto.getUrlMeeting());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("URL không hợp lệ!");
        }

        MeetingScheduleEntity entity = convertToEntity(dto);
        MeetingScheduleEntity savedEntity = meetingScheduleRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public MeetingScheduleDTO updateMeetingSchedule(Integer meetingId, MeetingScheduleDTO dto) {
        MeetingScheduleEntity entityToUpdate = meetingScheduleRepository.findById(meetingId)
                .orElseThrow(() -> new AppException(ErrorCode.MEET_SCHEDULE_NOT_FOUND, "Không tìm thấy lịch họp nào."));

        entityToUpdate.setMeetingDate(dto.getMeetingDate());
        CourseEntity courseEntity = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học nào."));
        entityToUpdate.setCourse(courseEntity);

        MeetingScheduleEntity updatedEntity = meetingScheduleRepository.save(entityToUpdate);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteMeetingSchedule(Integer meetingId) {
        if (!meetingScheduleRepository.existsById(meetingId)) {
            throw new AppException(ErrorCode.MEET_SCHEDULE_NOT_FOUND, "Không tìm thấy lịch họp nào.");
        }
        meetingScheduleRepository.deleteById(meetingId);
    }

    private MeetingScheduleDTO convertToDTO(MeetingScheduleEntity entity) {

        return new MeetingScheduleDTO(
                entity.getMeetingId(),
                entity.getCourse().getCourseId(),
                entity.getMeetingDate(),
                entity.getUrlMeeting(),
                entity.getCreatedAt()
        );
    }

    private MeetingScheduleEntity convertToEntity(MeetingScheduleDTO dto) {
        CourseEntity course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học nào."));

        MeetingScheduleEntity entity = new MeetingScheduleEntity();
        entity.setMeetingId(dto.getMeetingId());
        entity.setCourse(course);
        entity.setMeetingDate(dto.getMeetingDate());
        entity.setUrlMeeting(dto.getUrlMeeting());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
    @Override
    public List<MeetingScheduleDTO> getMeetingSchedulesByCourseId(Integer courseId) {
        // Kiểm tra xem khóa học có tồn tại hay không
        courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND, "Không tìm thấy khóa học nào."));

        // Tìm danh sách lịch học theo khóa học
        List<MeetingScheduleEntity> meetingSchedules = meetingScheduleRepository.findByCourse_CourseId(courseId);

        if (meetingSchedules.isEmpty()) {
            throw new AppException(ErrorCode.MEET_SCHEDULE_NOT_FOUND, "Không tìm thấy lịch họp nào cho khóa học này.");
        }

        return meetingSchedules.stream().map(this::convertToDTO).collect(Collectors.toList());
}
}
