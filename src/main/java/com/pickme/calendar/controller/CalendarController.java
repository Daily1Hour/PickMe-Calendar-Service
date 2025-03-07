package com.pickme.calendar.controller;

import com.pickme.calendar.dto.delete.DeleteApiResponseDTO;
import com.pickme.calendar.dto.get.GetCalendarDTO;
import com.pickme.calendar.dto.get.GetInterviewDTO;
import com.pickme.calendar.dto.post.PostApiResponseDTO;
import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
import com.pickme.calendar.dto.put.PutApiResponseDTO;
import com.pickme.calendar.dto.put.PutInterviewDetailDTO;
import com.pickme.calendar.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

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
    @Operation(summary = "면접 일정 전체&조건 조회", description = "면접 일정 전체 조회 & 특정 조건에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공", content = @Content(schema = @Schema(implementation = GetCalendarDTO.class)))
    @GetMapping("/interviews")
    public ResponseEntity<?> interviewsList(HttpServletRequest request,
                                            @Parameter(description = "회사 이름 (필터링 조건)", example = "앙떼띠")
                                            @RequestParam(required = false) String name,
                                            @Parameter(description = "조회할 년/월 (yyyyMM 형식, 필터링 조건)", example = "2024-11")
                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth) {

        String clientId = (String) request.getAttribute("clientId");

        log.info("clientId: " + clientId);

        return calendarService.interviewsList(clientId, name, yearMonth);
    }

    // 해당 사용자의 interviewDetailId에 해당하는 면접 일정 조회
    @Operation(summary = "면접 일정 조회", description = "interviewDetailId에 해당하는 면접 일정 조회")
    @ApiResponse(responseCode = "200", description = "조회 요청 성공", content = @Content(schema = @Schema(implementation = GetInterviewDTO.class)))
    @GetMapping("/interview")
    public ResponseEntity<?> Interview(@Parameter(description = "면접 일정 ID", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                       @RequestParam String interviewDetailId){

        return calendarService.getInterview(interviewDetailId);
    }

    // 면접 일정 추가
    @Operation(summary = "면접 일정 추가", description = "새로운 면접 일정 추가")
    @ApiResponse(responseCode = "200", description = "면접 일정 추가 성공", content = @Content(schema = @Schema(implementation = PostApiResponseDTO.class)))
    @PostMapping("/interview")
    public ResponseEntity<?> createInterviewSchedule(HttpServletRequest request,
                                                     @RequestBody PostInterviewDetailDTO postInterviewDetailDto){

        String clientId = (String) request.getAttribute("clientId");

        return calendarService.registerInterviewSchedule(postInterviewDetailDto, clientId);

    }

    // 면접 일정 삭제
    @Operation(summary = "면접 일정 삭제", description = "interviewDetailId에 해당하는 면접 일정 삭제")
    @ApiResponse(responseCode = "200", description = "면접 일정 삭제 성공", content = @Content(schema = @Schema(implementation = DeleteApiResponseDTO.class)))
    @DeleteMapping("/interview")
    public ResponseEntity<?> deleteInterviewSchedule(@Parameter(description = "면접 일정 ID (필터링 조건)", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                                     @RequestParam String interviewDetailId){

        return calendarService.deleteInterviewSchedule(interviewDetailId);
    }

    // 특정 면접 일정 수정
    @Operation(summary = "면접 일정 수정", description = "interviewDetailId에 해당하는 면접 일정 수정")
    @ApiResponse(responseCode = "200", description = "면접 일정 수정 성공", content = @Content(schema = @Schema(implementation = PutApiResponseDTO.class)))
    @PutMapping("/interview")
    public ResponseEntity<?> putInterviewSchedule(@Parameter(description = "면접 일정 ID (필터링 조건)", example = "27e725b8-5816-4783-a4d0-7a19e7ae4f34")
                                                  @RequestParam String interviewDetailId,
                                                  @RequestBody PutInterviewDetailDTO putInterviewDetailDTO){

        return calendarService.putInterviewSchedule(interviewDetailId, putInterviewDetailDTO);
    }

}
