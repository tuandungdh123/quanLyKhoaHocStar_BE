package com.example.coursemanagement.api;

import com.example.coursemanagement.data.DTO.MeetingScheduleDTO;
import com.example.coursemanagement.data.mgt.ResponseObject;
import com.example.coursemanagement.service.MeetingScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/meet")
@CrossOrigin(origins = "*")
public class MeetingScheduleApi {
    @Autowired
    private MeetingScheduleService meetingScheduleService;

    @GetMapping("/getAllMeetingSchedule")
    public ResponseObject<?> getAllMeetingSchedules() {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(meetingScheduleService.getAllMeetingSchedules());
            resultApi.setSuccess(true);
            resultApi.setMessage("getAllMeetingSchedules success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/getAll", e);
        }
        return resultApi;
    }

    @GetMapping("/getMeetingScheduleById")
    public ResponseObject<?> getMeetingScheduleById(@RequestBody MeetingScheduleDTO scheduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(meetingScheduleService.getMeetingScheduleById(scheduleDTO.getMeetingId()));
            resultApi.setSuccess(true);
            resultApi.setMessage("getMeetingScheduleById success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/getById", e);
        }
        return resultApi;
    }

    @PostMapping("/createMeetingSchedule")
    public ResponseObject<?> createMeetingSchedule(@RequestBody MeetingScheduleDTO scheduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(meetingScheduleService.createMeetingSchedule(scheduleDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("createMeetingSchedule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/create", e);
        }
        return resultApi;
    }

    @PutMapping("/updateMeetingSchedule")
    public ResponseObject<?> updateMeetingSchedule(@RequestBody MeetingScheduleDTO scheduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            resultApi.setData(meetingScheduleService.updateMeetingSchedule(
                    scheduleDTO.getMeetingId(), scheduleDTO));
            resultApi.setSuccess(true);
            resultApi.setMessage("updateMeetingSchedule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/update", e);
        }
        return resultApi;
    }

    @DeleteMapping("/deleteMeetingSchedule")
    public ResponseObject<?> deleteMeetingSchedule(@RequestBody MeetingScheduleDTO scheduleDTO) {
        var resultApi = new ResponseObject<>();
        try {
            meetingScheduleService.deleteMeetingSchedule(scheduleDTO.getMeetingId());
            resultApi.setSuccess(true);
            resultApi.setMessage("deleteMeetingSchedule success");
        } catch (Exception e) {
            resultApi.setSuccess(false);
            resultApi.setMessage(e.getMessage());
            log.error("Fail When Call API /api/v1/meeting-schedule/delete", e);
        }
        return resultApi;
    }

        @GetMapping("/getMeetingSchedulesByCourseId")
        public ResponseObject<?> getMeetingSchedulesByCourseId(@RequestParam Integer courseId) {
            var resultApi = new ResponseObject<>();
            try {
                resultApi.setData(meetingScheduleService.getMeetingSchedulesByCourseId(courseId));
                resultApi.setSuccess(true);
                resultApi.setMessage("getMeetingSchedulesByCourseId success");
            } catch (Exception e) {
                resultApi.setSuccess(false);
                resultApi.setMessage(e.getMessage());
            }
            return resultApi;
        }
}
