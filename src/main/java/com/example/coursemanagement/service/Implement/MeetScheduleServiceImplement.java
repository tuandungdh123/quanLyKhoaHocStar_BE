package com.example.coursemanagement.service.Implement;

import com.example.coursemanagement.data.DTO.MeetingScheduleDTO;
import com.example.coursemanagement.data.entity.CourseEntity;
import com.example.coursemanagement.data.entity.MeetingScheduleEntity;
import com.example.coursemanagement.repository.CourseRepository;
import com.example.coursemanagement.repository.MeetingScheduleRepository;
import com.example.coursemanagement.service.MeetingScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetScheduleServiceImplement implements MeetingScheduleService {
    @Autowired
    private MeetingScheduleRepository meetingScheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<MeetingScheduleDTO> getAllMeetingSchedules() {
        return meetingScheduleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingScheduleDTO getMeetingScheduleById(Integer meetingId) {
        Optional<MeetingScheduleEntity> entity = meetingScheduleRepository.findById(meetingId);
        if (entity.isEmpty()) {
            throw new RuntimeException("Meeting Schedule with ID " + meetingId + " not found");
        }
        return convertToDTO(entity.get());
    }

    @Override
    public MeetingScheduleDTO createMeetingSchedule(MeetingScheduleDTO dto) {
        MeetingScheduleEntity entity = convertToEntity(dto);
        MeetingScheduleEntity savedEntity = meetingScheduleRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public MeetingScheduleDTO updateMeetingSchedule(Integer meetingId, MeetingScheduleDTO dto) {
        Optional<MeetingScheduleEntity> existingEntity = meetingScheduleRepository.findById(meetingId);
        if (existingEntity.isEmpty()) {
            throw new RuntimeException("Meeting Schedule with ID " + meetingId + " not found");
        }

        MeetingScheduleEntity entityToUpdate = existingEntity.get();
        entityToUpdate.setMeetingDate(dto.getMeetingDate());
        CourseEntity courseEntity = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course with ID " + dto.getCourseId() + " not found"));
        entityToUpdate.setCourse(courseEntity);

        MeetingScheduleEntity updatedEntity = meetingScheduleRepository.save(entityToUpdate);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void deleteMeetingSchedule(Integer meetingId) {
        if (!meetingScheduleRepository.existsById(meetingId)) {
            throw new RuntimeException("Meeting Schedule with ID " + meetingId + " not found");
        }
        meetingScheduleRepository.deleteById(meetingId);
    }

    private MeetingScheduleDTO convertToDTO(MeetingScheduleEntity entity) {
        return new MeetingScheduleDTO(
                entity.getMeetingId(),
                entity.getCourse().getCourseId(),
                entity.getMeetingDate(),
                entity.getCreatedAt()
        );
    }

    private MeetingScheduleEntity convertToEntity(MeetingScheduleDTO dto) {
        MeetingScheduleEntity entity = new MeetingScheduleEntity();

        CourseEntity course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + dto.getCourseId()));

        entity.setMeetingId(dto.getMeetingId());
        entity.setCourse(course);
        entity.setMeetingDate(dto.getMeetingDate());
        entity.setCreatedAt(dto.getCreatedAt());

        return entity;
    }
}
