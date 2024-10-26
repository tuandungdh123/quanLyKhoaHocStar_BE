package com.example.coursemanagement.service;

import com.example.coursemanagement.data.DTO.MeetingScheduleDTO;


import java.util.List;

public interface MeetingScheduleService {
    List<MeetingScheduleDTO> getAllMeetingSchedules();

    MeetingScheduleDTO getMeetingScheduleById(Integer meetingId);

    MeetingScheduleDTO createMeetingSchedule(MeetingScheduleDTO scheduleDTO);

    MeetingScheduleDTO updateMeetingSchedule(Integer meetingId, MeetingScheduleDTO scheduleDTO);

    void deleteMeetingSchedule(Integer meetingId);

}
