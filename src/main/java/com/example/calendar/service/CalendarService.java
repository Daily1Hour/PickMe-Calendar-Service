package com.example.calendar.service;

import com.example.calendar.entity.Calendar;
import com.example.calendar.repository.CalendarRepository;
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
}
