package com.example.calendar.service;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.repository.CalendarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public List<Calendar> interviewsList(String userinfo){
        return calendarRepository.findByUserInfo(userinfo);
    }

    public boolean register_interview_schedule(InterviewScheduleDto interviewScheduleDto, String token) {
        Calendar calendar = new Calendar();
        calendar.setUserInfo(token);
        calendar.setLocation(interviewScheduleDto.getLocation());
        calendar.setCompanyName(interviewScheduleDto.getCompanyName());
        calendar.setPosition(interviewScheduleDto.getPosition());
        calendar.setInterviewTime(interviewScheduleDto.getInterviewTime());
        calendarRepository.save(calendar);
        return true;
    }

    public boolean delete_interview_schedule(String id) {
        calendarRepository.deleteById(id);
        return true;
    }

    public InterviewScheduleDto put_interview_schedule(String id, InterviewScheduleDto interviewScheduleDto) {

        // calendarRepository.findById(id)로 반환된 Optional<Calendar>에서 값을 가져옴
        // 값이 없으면 EntityNotFoundException을 던져 예외를 처리함
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found with id: " + id));

        calendar.setCompanyName(interviewScheduleDto.getCompanyName());
        calendar.setLocation(interviewScheduleDto.getLocation());
        calendar.setInterviewTime(interviewScheduleDto.getInterviewTime());
        calendar.setPosition(interviewScheduleDto.getPosition());
        calendarRepository.save(calendar);

        InterviewScheduleDto interviewScheduleDto1 = new InterviewScheduleDto();
        interviewScheduleDto1.setCompanyName(calendar.getCompanyName());
        interviewScheduleDto1.setLocation(calendar.getLocation());
        interviewScheduleDto1.setInterviewTime(calendar.getInterviewTime());
        interviewScheduleDto1.setPosition(calendar.getPosition());

        return interviewScheduleDto1;
    }
}
