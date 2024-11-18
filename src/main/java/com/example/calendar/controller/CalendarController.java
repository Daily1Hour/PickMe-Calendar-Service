package com.example.calendar.controller;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.dto.ResponseDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.extract.ExtractToken;
import com.example.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 해당 사용자 면접 일정 전체 조회
    @GetMapping("/interviews")
    public List<Calendar> interviewsList(@RequestHeader("Authorization") String token,
                                         @RequestParam(required = false) String position,
                                         @RequestParam(required = false) LocalDateTime startDate,
                                         @RequestParam(required = false) LocalDateTime endDate){

        String extractedToken = ExtractToken.extractToken(token);

        log.info("Extracted token: " + extractedToken);
        log.info("position: " + position);
        log.info("startDate: " + startDate);
        log.info("endDate: " + endDate);

        // 조건 처리
        if (position == null && startDate == null && endDate == null) {
            log.info("직무와 날짜가 모두 입력되지 않았습니다.");
            // 기본 전체 데이터를 반환하거나 예외 처리
            return calendarService.interviewsList(extractedToken);
        } else if (position != null && startDate == null && endDate == null) {
            log.info("직무만 입력되었습니다: " + position);
            // 직무에 해당하는 인터뷰 데이터를 반환
            return calendarService.interviewsListByJob(extractedToken, position);
        } else if (position == null && (startDate != null || endDate != null)) {
            log.info("날짜만 입력되었습니다: " + startDate + " ~ " + endDate);
            // 날짜 범위에 해당하는 인터뷰 데이터를 반환
            return calendarService.interviewsListByPeriod(extractedToken, startDate, endDate);
        } else if (position != null && (startDate != null || endDate != null)) {
            log.info("직무와 날짜가 모두 입력되었습니다. 직무: " + position + ", 날짜: " + startDate + " ~ " + endDate);
            // 직무와 날짜 조건을 모두 만족하는 인터뷰 데이터를 반환
            return calendarService.InterviewsByPositionAndDate(extractedToken, position, startDate, endDate);
        }

        // 모든 조건에 맞지 않는 경우 기본 값 반환 (필요시 추가)
        log.warn("알 수 없는 조건으로 요청되었습니다.");
        return Collections.emptyList();

    }

    // 면접 일정 추가
    @PostMapping("/interviews")
    public ResponseDto registerInterviewSchedule(@RequestHeader("Authorization") String token, @RequestBody InterviewScheduleDto interviewScheduleDto){
        String extractedToken = ExtractToken.extractToken(token);

        // 받아온 데이터를 DB에 저장 요청
        boolean b = calendarService.registerInterviewSchedule(interviewScheduleDto, extractedToken);

        // 저장 성공 시 OK 반환
        if(b){

            ResponseDto responseDto = new ResponseDto();
            responseDto.setMessage("OK");
            return responseDto;
        }
        // 저장 실패 시 Fail 반환
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Fail");
        return responseDto;

    }

    // 면접 일정 삭제
    @DeleteMapping("/interviews")
    public ResponseDto deleteInterviewSchedule(@RequestHeader("Authorization") String token, @RequestParam String id){
        String extractedToken = ExtractToken.extractToken(token);
        boolean b = calendarService.deleteInterviewSchedule(id);

        if(b){
            ResponseDto responseDto = new ResponseDto();
            responseDto.setMessage("OK");
            return responseDto;
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Fail");
        return responseDto;
    }

    // 특정 면접 일정 수정
    @PutMapping("/interviews")
    public InterviewScheduleDto putInterviewSchedule(
            @RequestHeader("Authorization") String token,
            @RequestParam String id,
            @RequestBody InterviewScheduleDto interviewScheduleDto){

        String extractedToken = ExtractToken.extractToken(token);

        return calendarService.putInterviewSchedule(id, interviewScheduleDto);


    }

}
