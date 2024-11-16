package com.example.calendar.service;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.mapper.CalendarMapper;
import com.example.calendar.repository.CalendarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public List<Calendar> interviewsList(String userinfo){
        return calendarRepository.findByUserInfo(userinfo);
    }

    public boolean registerInterviewSchedule(InterviewScheduleDto interviewScheduleDto, String token) {
        Calendar calendar = new Calendar();
        calendar.setUserInfo(token);
        calendarMapper.toCalendarEntity(interviewScheduleDto, calendar);
        calendarRepository.save(calendar);
        return true;
    }

    public boolean deleteInterviewSchedule(String id) {
        calendarRepository.deleteById(id);
        return true;
    }

    public InterviewScheduleDto putInterviewSchedule(String id, InterviewScheduleDto interviewScheduleDto) {

        // calendarRepository.findById(id)로 반환된 Optional<Calendar>에서 값을 가져옴
        // 값이 없으면 EntityNotFoundException을 던져 예외를 처리함
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found with id: " + id));

        calendarMapper.toCalendarEntity(interviewScheduleDto, calendar);
        calendarRepository.save(calendar);
        return calendarMapper.toInterviewScheduleDto(calendar);
    }

    public List<Calendar> interviewsListByJob(String extractedToken, String position) {

        return calendarRepository.findByUserInfoAndPosition(extractedToken, position);
    }

    public List<Calendar> interviewsListByPeriod(String extractedToken, LocalDateTime startDate, LocalDateTime endDate) {
        return calendarRepository.findByUserInfoAndInterviewTimeBetween(extractedToken, startDate, endDate);
    }
}
