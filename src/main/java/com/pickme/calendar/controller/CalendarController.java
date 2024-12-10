package com.pickme.calendar.controller;

import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
import com.pickme.calendar.dto.put.PutInterviewDetailDTO;
import com.pickme.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Calendar", description = "면접 캘린더 API")
@ApiResponse(responseCode = "400", description = "잘못된 요청")
@ApiResponse(responseCode = "401", description = "권한 없음")
@ApiResponse(responseCode = "404", description = "면접 일정이 없음")
public class CalendarController {

    private final CalendarService calendarService;

    // 해당 사용자 면접 일정 전체 조회
    @Operation(summary = "면접 일정 조회", description = "면접 일정 전체 조회 & 특정 조건에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공")
    @GetMapping("/interviews")
    public ResponseEntity<?> interviewsList(HttpServletRequest request,
                                            @Parameter(description = "면접 일정 ID (필터링 조건)", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                            @RequestParam(required = false) String interviewDetailId,
                                            @Parameter(description = "회사 이름 (필터링 조건)", example = "앙떼띠")
                                            @RequestParam(required = false) String name,
                                            @Parameter(description = "조회할 년/월 (yyyyMM 형식, 필터링 조건)", example = "202411")
                                            @RequestParam(required = false) String yearMonth) {

        String clientId = (String) request.getAttribute("clientId");

        log.info("clientId: " + clientId);

        return calendarService.interviewsList(clientId, interviewDetailId ,name, yearMonth);
    }

    // 면접 일정 추가
    @Operation(summary = "면접 일정 추가", description = "새로운 면접 일정 추가")
    @ApiResponse(responseCode = "200", description = "면접 일정 추가 성공")
    @PostMapping("/interviews")
    public ResponseEntity<?> createInterviewSchedule(HttpServletRequest request,
                                                     @RequestBody PostInterviewDetailDTO postInterviewDetailDto){

        String clientId = (String) request.getAttribute("clientId");

        // 받아온 데이터를 DB에 저장 요청
        boolean b = calendarService.registerInterviewSchedule(postInterviewDetailDto, clientId);

        // 저장 성공 시 OK 반환
        if(b){
            return ResponseEntity.status(HttpStatus.OK).body("면접 일정 추가 성공");
        }
        // 저장 실패 시 Fail 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    // 면접 일정 삭제
    @Operation(summary = "면접 일정 삭제", description = "interviewDetailId에 해당하는 면접 일정 삭제")
    @ApiResponse(responseCode = "200", description = "면접 일정 삭제 성공")
    @DeleteMapping("/interviews")
    public ResponseEntity<?> deleteInterviewSchedule(HttpServletRequest request,
                                                     @Parameter(description = "면접 일정 ID (필터링 조건)", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                                     @RequestParam String interviewDetailId){

        String clientId = (String) request.getAttribute("clientId");

        return calendarService.deleteInterviewSchedule(clientId, interviewDetailId);
    }

    // 특정 면접 일정 수정
    @Operation(summary = "면접 일정 수정", description = "interviewDetailId에 해당하는 면접 일정 수정")
    @ApiResponse(responseCode = "200", description = "면접 일정 수정 성공")
    @PutMapping("/interviews")
    public ResponseEntity<?> putInterviewSchedule(HttpServletRequest request,
                                                  @Parameter(description = "면접 일정 ID (필터링 조건)", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                                  @RequestParam String interviewDetailId,
                                                  @RequestBody PutInterviewDetailDTO putInterviewDetailDTO){

        String clientId = (String) request.getAttribute("clientId");

        return calendarService.putInterviewSchedule(clientId, interviewDetailId, putInterviewDetailDTO);
    }

}
