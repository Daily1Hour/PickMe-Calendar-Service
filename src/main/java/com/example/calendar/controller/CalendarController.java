package com.example.calendar.controller;

import com.example.calendar.entity.Calendar;
import com.example.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 해당 사용자 면접 일정 전체 조회
    @GetMapping("/interviews")
    public List<Calendar> interviewsList(@RequestHeader("Authorization") String token){
        String[] parts = token.split(" ");  // 공백 기준으로 나누기
        String extractedToken = parts[1];   // 두 번째 요소가 실제 토큰
        log.info("Extracted token: " + extractedToken);
        return calendarService.interviewsList(extractedToken);
    }

}
