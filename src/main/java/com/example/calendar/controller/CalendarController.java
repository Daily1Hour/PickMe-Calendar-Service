package com.example.calendar.controller;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.dto.ResponseDto;
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

    // 특정 직무 면접 일정 조회
    @GetMapping("/interviews/byJob")
    public List<Calendar> interviewsList_byJob(@RequestHeader("Authorization") String token, @RequestParam String position){
        String[] parts = token.split(" ");  // 공백 기준으로 나누기
        String extractedToken = parts[1];   // 두 번째 요소가 실제 토큰
        log.info("Extracted token: " + extractedToken + "position : " + position);
        return calendarService.interviewsList_byJob(extractedToken, position);
    }


    // 면접 일정 추가
    @PostMapping("/interviews")
    public ResponseDto register_interview_schedule(@RequestHeader("Authorization") String token, @RequestBody InterviewScheduleDto interviewScheduleDto){
        String[] parts = token.split(" ");  // 공백 기준으로 나누기
        String extractedToken = parts[1];   // 두 번째 요소가 실제 토큰

        // 받아온 데이터를 DB에 저장 요청
        boolean b = calendarService.register_interview_schedule(interviewScheduleDto, extractedToken);

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
    public ResponseDto delete_interview_schedule(@RequestHeader("Authorization") String token, @RequestParam String id){
        String[] parts = token.split(" ");
        String extractedToken = parts[1];
        boolean b = calendarService.delete_interview_schedule(id);

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
    public InterviewScheduleDto put_interview_schedule(
            @RequestHeader("Authorization") String token,
            @RequestParam String id,
            @RequestBody InterviewScheduleDto interviewScheduleDto){

        String[] parts = token.split(" ");
        String extractedToken = parts[1];

        return calendarService.put_interview_schedule(id, interviewScheduleDto);


    }

}
