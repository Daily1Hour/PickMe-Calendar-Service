package com.pickme.calendar.controller;

import com.pickme.calendar.dto.InterviewScheduleDTO;
import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
import com.pickme.calendar.service.CalendarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // 해당 사용자 면접 일정 전체 조회
    @GetMapping("/interviews")
    public ResponseEntity<?> interviewsList(HttpServletRequest request,
                                            @RequestParam(required = false) String name) {

        String userInfo = (String) request.getAttribute("userInfo");

        log.info("userInfo: " + userInfo);

        return calendarService.interviewsList(userInfo, name);
    }

    // 면접 일정 추가
    @PostMapping("/interviews")
    public ResponseEntity<?> createInterviewSchedule(HttpServletRequest request,
                                                     @RequestBody PostInterviewDetailDTO postInterviewDetailDto){

        String extractedToken = (String) request.getAttribute("userInfo");

        // 받아온 데이터를 DB에 저장 요청
        boolean b = calendarService.registerInterviewSchedule(postInterviewDetailDto, extractedToken);

        // 저장 성공 시 OK 반환
        if(b){
            return ResponseEntity.status(HttpStatus.OK).body("Create Success");
        }
        // 저장 실패 시 Fail 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 면접 일정 삭제
    @DeleteMapping("/interviews")
    public ResponseEntity<?> deleteInterviewSchedule(HttpServletRequest request,
                                                     @RequestParam String id){

        String extractedToken = (String) request.getAttribute("userInfo");

        boolean b = calendarService.deleteInterviewSchedule(extractedToken, id);

        if(b){

            return ResponseEntity.status(HttpStatus.OK).body("Delete Success");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 특정 면접 일정 수정
    @PutMapping("/interviews")
    public ResponseEntity<?> putInterviewSchedule(HttpServletRequest request,
                                                  @RequestParam String id,
                                                  @RequestBody InterviewScheduleDTO interviewScheduleDto){

        String extractedToken = (String) request.getAttribute("userInfo");

        return calendarService.putInterviewSchedule(extractedToken, id, interviewScheduleDto);
    }

}
