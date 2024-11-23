package com.pickme.calendar.service;

import com.pickme.calendar.dto.InterviewScheduleDto;
import com.pickme.calendar.dto.post.PostInterviewDetailDto;
import com.pickme.calendar.entity.Calendar;
import com.pickme.calendar.exception.CustomException;
import com.pickme.calendar.exception.ErrorCode;
import com.pickme.calendar.service.mapper.CalendarMapper;
import com.pickme.calendar.repository.CalendarRepository;
import com.pickme.calendar.repository.CalendarMongoQueryProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;
    private final CalendarMongoQueryProcessor calendarMongoQueryProcessor;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public ResponseEntity<?> interviewsList(String userInfo, String name){
        //Optional<Calendar> optionalCalendar = calendarRepository.findByUserInfo(userInfo);
        if (calendarRepository.existsByUserInfo(userInfo)){
            Calendar calendar = calendarRepository.findByUserInfo(userInfo);
            List<Calendar.InterviewDetails> interviewDetails = calendarMongoQueryProcessor.findCalendar(calendar, name);
            return ResponseEntity.status(HttpStatus.OK).body(interviewDetails);
        }

        return ResponseEntity.status(HttpStatus.OK).body("성공");
    }

    // 사용자의 면접 일정 추가
    public boolean registerInterviewSchedule(PostInterviewDetailDto postInterviewDetailDto, String token) {
        Calendar calendar;

        if(calendarRepository.existsByUserInfo(token)){
            calendar = calendarRepository.findByUserInfo(token);
        } else {
            calendar = new Calendar();
            calendar.setUserInfo(token);
            calendar.setInterviewDetails(new ArrayList<>());
        }

        Calendar.InterviewDetails interviewDetails = new Calendar.InterviewDetails();
        calendarMapper.postInterviewDetailDtoToInterviewDetails(postInterviewDetailDto, interviewDetails);
        calendar.getInterviewDetails().add(interviewDetails);
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
