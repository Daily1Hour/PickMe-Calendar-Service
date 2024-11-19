package com.example.calendar.service;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.mapper.CalendarMapper;
import com.example.calendar.repository.CalendarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public ResponseEntity<?> interviewsList(String userInfo, String position, LocalDateTime startDate, LocalDateTime endDate){
        // 조건 처리
        if (position == null && startDate == null && endDate == null) {
            log.info("직무와 날짜가 모두 입력되지 않았습니다.");
            // 기본 전체 데이터를 반환하거나 예외 처리
            return ResponseEntity.status(HttpStatus.OK).body(calendarRepository.findByUserInfo(userInfo));
        } else if (position != null && startDate == null && endDate == null) {
            log.info("직무만 입력되었습니다: " + position);
            // 직무에 해당하는 인터뷰 데이터를 반환
            return ResponseEntity.status(HttpStatus.OK).body(calendarRepository.findByUserInfoAndPosition(userInfo, position));
        } else if (position == null && (startDate != null && endDate != null)) {
            log.info("날짜만 입력되었습니다: " + startDate + " ~ " + endDate);
            // 날짜 범위에 해당하는 인터뷰 데이터를 반환
            return ResponseEntity.status(HttpStatus.OK).body(calendarRepository.findByUserInfoAndInterviewTimeBetween(userInfo, startDate, endDate));
        } else if (position != null && (startDate != null && endDate != null)) {
            log.info("직무와 날짜가 모두 입력되었습니다. 직무: " + position + ", 날짜: " + startDate + " ~ " + endDate);
            // 직무와 날짜 조건을 모두 만족하는 인터뷰 데이터를 반환
            return ResponseEntity.status(HttpStatus.OK).body(calendarRepository.findByUserInfoAndPositionAndInterviewTimeBetween(userInfo, position, startDate, endDate));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("날짜 입력값이 잘못되었습니다. startDate와 endDate는 둘 다 입력되어야 합니다.");
        }
    }

    // 사용자의 면접 일정 추가
    public boolean registerInterviewSchedule(InterviewScheduleDto interviewScheduleDto, String token) {
        Calendar calendar = new Calendar();
        calendar.setUserInfo(token);
        calendarMapper.toCalendarEntity(interviewScheduleDto, calendar);
        calendarRepository.save(calendar);
        return true;
    }

    // 사용자의 면접 일정 삭제
    public boolean deleteInterviewSchedule(String id) {
        calendarRepository.deleteById(id);
        return true;
    }

    // 사용자의 면접 일정 수정
    public InterviewScheduleDto putInterviewSchedule(String id, InterviewScheduleDto interviewScheduleDto) {

        // calendarRepository.findById(id)로 반환된 Optional<Calendar>에서 값을 가져옴
        // 값이 없으면 EntityNotFoundException을 던져 예외를 처리함
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found with id: " + id));

        calendarMapper.toCalendarEntity(interviewScheduleDto, calendar);
        calendarRepository.save(calendar);
        return calendarMapper.toInterviewScheduleDto(calendar);
    }

}
