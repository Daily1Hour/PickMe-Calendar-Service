package com.example.calendar.controller;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.extract.ExtractToken;
import com.example.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 해당 사용자 면접 일정 전체 조회
    @GetMapping("/interviews")
    public ResponseEntity<?> interviewsList(@RequestHeader("Authorization") String token,
                                         @RequestParam(required = false) String position,
                                         @RequestParam(required = false) LocalDateTime startDate,
                                         @RequestParam(required = false) LocalDateTime endDate){

        String extractedToken = ExtractToken.extractToken(token);

        log.info("Extracted token: " + extractedToken);
        log.info("position: " + position);
        log.info("startDate: " + startDate);
        log.info("endDate: " + endDate);

        return calendarService.interviewsList(extractedToken, position, startDate, endDate);
    }

    // 면접 일정 추가
    @PostMapping("/interviews")
    public ResponseEntity<?> registerInterviewSchedule(@RequestHeader("Authorization") String token, @RequestBody InterviewScheduleDto interviewScheduleDto){
        String extractedToken = ExtractToken.extractToken(token);

        // 받아온 데이터를 DB에 저장 요청
        boolean b = calendarService.registerInterviewSchedule(interviewScheduleDto, extractedToken);

        // 저장 성공 시 OK 반환
        if(b){
            return ResponseEntity.status(HttpStatus.OK).body("Create Success");
        }
        // 저장 실패 시 Fail 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 면접 일정 삭제
    @DeleteMapping("/interviews")
    public ResponseEntity<?> deleteInterviewSchedule(@RequestHeader("Authorization") String token, @RequestParam String id){
        String extractedToken = ExtractToken.extractToken(token);
        boolean b = calendarService.deleteInterviewSchedule(extractedToken, id);

        if(b){

            return ResponseEntity.status(HttpStatus.OK).body("Delete Success");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 특정 면접 일정 수정
    @PutMapping("/interviews")
    public ResponseEntity<?> putInterviewSchedule(
            @RequestHeader("Authorization") String token,
            @RequestParam String id,
            @RequestBody InterviewScheduleDto interviewScheduleDto){

        String extractedToken = ExtractToken.extractToken(token);

        return calendarService.putInterviewSchedule(extractedToken, id, interviewScheduleDto);
    }

}
