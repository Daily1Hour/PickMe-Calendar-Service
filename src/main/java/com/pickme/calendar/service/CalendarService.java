package com.pickme.calendar.service;

import com.pickme.calendar.dto.InterviewScheduleDTO;
import com.pickme.calendar.dto.get.GetCalendarDTO;
import com.pickme.calendar.dto.get.GetInterviewDetailDTO;
import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;
    private final CalendarMongoQueryProcessor calendarMongoQueryProcessor;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public ResponseEntity<?> interviewsList(String userInfo, String name){

        // 사용자의 면접 일정이 존재하는지 확인
        if (calendarRepository.existsByUserInfo(userInfo)){
            // 사용자의 면접 일정 전체를 Calendar 객체로 가져옴
            Calendar calendar = calendarRepository.findByUserInfo(userInfo);
            // 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴
            List<Calendar.InterviewDetails> interviewDetails = calendarMongoQueryProcessor.filterInterviewDetails(calendar, name);

            // 응답을 위한 GetCalendarDTO 객체 생성
            GetCalendarDTO getCalendarDTO = new GetCalendarDTO();
            // Calendar 엔티티의 정보를 GetCalendarDTO로 매핑 (id, userInfo 등)
            calendarMapper.calendarToGetCalendarDTO(calendar, getCalendarDTO);

            // interviewDetails 리스트를 GetInterviewDetailDTO 객체로 변환
            List<GetInterviewDetailDTO> getInterviewListDTOList = calendarMapper.interviewDetailsToGetInterviewDetailsDTO(interviewDetails);
            // 변환된 interviewDetails 리스트를 GetCalendarDTO 객체에 설정
            getCalendarDTO.setInterviewDetails(getInterviewListDTOList);

            return ResponseEntity.status(HttpStatus.OK).body(getCalendarDTO);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 조건의 사용자 면접 일정이 없습니다.");
        }
    }

    // 사용자의 면접 일정 추가
    public boolean registerInterviewSchedule(PostInterviewDetailDTO postInterviewDetailDto, String token) {

        Calendar calendar;

        // 사용자 면접 일정 정보가 존재하는 지 확인
        if(calendarRepository.existsByUserInfo(token)){
            // 사용자의 면접 일정을 Calendar 객체로 가져옴
            calendar = calendarRepository.findByUserInfo(token);
        } else {
            // 사용자의 면접 일정이 없다면 새로운 Calendar 객체 생성
            calendar = new Calendar();
            calendar.setUserInfo(token); // 사용자 정보 설정
            calendar.setInterviewDetails(new ArrayList<>()); // 빈 면접 일정 리스트 초기화
        }

        // 새로운 InterviesDetails 객체 생성
        Calendar.InterviewDetails interviewDetails = new Calendar.InterviewDetails();
        // InterviesDetails 객체의 interviewDetailId 값 설정
        interviewDetails.setInterviewDetailId(UUID.randomUUID().toString());
        // 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetails 객체로 변환
        calendarMapper.postInterviewDetailDtoToInterviewDetails(postInterviewDetailDto, interviewDetails);
        // 변환된 interviewDetails를 Calendar의 interviewDetails 리스트에 추가
        calendar.getInterviewDetails().add(interviewDetails);
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        calendarRepository.save(calendar);
        // 면접 일정 등록 완료 후 true 반환
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
    public ResponseEntity<?> putInterviewSchedule(String userInfo, String id, InterviewScheduleDTO interviewScheduleDto) {
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
