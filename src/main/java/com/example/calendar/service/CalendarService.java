package com.example.calendar.service;

import com.example.calendar.dto.InterviewScheduleDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.exception.CustomException;
import com.example.calendar.exception.ErrorCode;
import com.example.calendar.mapper.CalendarMapper;
import com.example.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public ResponseEntity<?> interviewsList(String userInfo, String position, LocalDateTime startDate, LocalDateTime endDate){
        Optional<List<Calendar>> optionalCalendarList = Optional.ofNullable(calendarRepository.findByUserInfo(userInfo));
        log.info(optionalCalendarList.get().toString());
        if(optionalCalendarList.get().isEmpty()){
            throw new CustomException(ErrorCode.NULL_USERINFO, userInfo + "님의 면접 일정은 없습니다.");
        }

        List<Calendar> calendarList;
        // 조건 처리
        if (position == null && startDate == null && endDate == null) { // 면접 일정 전체 조회
            // 기본 전체 데이터를 반환하거나 예외 처리
            calendarList = calendarRepository.findByUserInfo(userInfo);
        } else if (position != null && startDate == null && endDate == null) { // 직무별 면접 일정 조회
            // 직무에 해당하는 인터뷰 데이터를 반환
            calendarList = calendarRepository.findByUserInfoAndPosition(userInfo, position);
        } else if (position == null && (startDate != null && endDate != null)) { // 특정 기간 내 면접 일정 조회
            // 날짜 범위에 해당하는 인터뷰 데이터를 반환
            calendarList = calendarRepository.findByUserInfoAndInterviewTimeBetween(userInfo, startDate, endDate);
        } else if (position != null && (startDate != null && endDate != null)) { // 특정 직무의 특정 기간 내 면접 일정 조회
            // 직무와 날짜 조건을 모두 만족하는 인터뷰 데이터를 반환
            calendarList = calendarRepository.findByUserInfoAndPositionAndInterviewTimeBetween(userInfo, position, startDate, endDate);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("날짜 입력값이 잘못되었습니다. startDate와 endDate는 둘 다 입력되어야 합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(calendarMapper.toGetInterviewListDto(calendarList));
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
    public boolean deleteInterviewSchedule(String userInfo, String id) {
        Optional<Calendar> optionalCalendar = Optional.ofNullable(calendarRepository.findByUserInfoAndId(userInfo, id));
        if(optionalCalendar.isEmpty()){
            throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND, String.format("%s 님의 %s id에 해당하는 면접 일정은 없습니다.", userInfo,id));
        }
        calendarRepository.deleteByUserInfoAndId(userInfo, id);
        return true;
    }

    // 사용자의 면접 일정 수정
    public ResponseEntity<?> putInterviewSchedule(String userInfo, String id, InterviewScheduleDto interviewScheduleDto) {
        Optional<Calendar> optionalCalendar = Optional.ofNullable(calendarRepository.findByUserInfoAndId(userInfo, id));
        if(optionalCalendar.isEmpty()){
            throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND, String.format("%s 님의 %s id에 해당하는 면접 일정은 없습니다.", userInfo,id));
        }

        // calendarRepository.findByUserInfoAndId(userInfo, id)로 반환된 해당 일정을 가져옴
        Calendar calendar = calendarRepository.findByUserInfoAndId(userInfo, id);

        calendarMapper.toCalendarEntity(interviewScheduleDto, calendar);
        calendarRepository.save(calendar);
        return ResponseEntity.status(HttpStatus.OK).body(calendarMapper.toInterviewScheduleDto(calendar));
    }

}
