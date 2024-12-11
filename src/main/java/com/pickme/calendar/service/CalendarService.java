package com.pickme.calendar.service;

import com.pickme.calendar.dto.get.GetCalendarDTO;
import com.pickme.calendar.dto.get.GetInterviewDetailDTO;
import com.pickme.calendar.dto.post.PostInterviewDetailDTO;
import com.pickme.calendar.dto.put.PutInterviewDetailDTO;
import com.pickme.calendar.entity.Calendar;
import com.pickme.calendar.service.mapper.CalendarMapper;
import com.pickme.calendar.repository.CalendarRepository;
import com.pickme.calendar.repository.CalendarMongoQueryProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarMapper calendarMapper;
    private final CalendarMongoQueryProcessor calendarMongoQueryProcessor;

    // 해당 사용자의 면접 일정 전체 조회 레포지토리에 요청
    public ResponseEntity<?> interviewsList(String clientId, String interviewDetailId, String name, YearMonth yearMonth){

        // 사용자의 면접 일정이 존재하는지 확인
        if (calendarRepository.existsByClientId(clientId)){
            // 사용자의 면접 일정 전체를 Calendar 객체로 가져옴
            Calendar calendar = calendarRepository.findByClientId(clientId);
            // 주어진 조건(현재는 name)으로 필터링된 interviewDetails 리스트를 가져옴
            List<Calendar.InterviewDetails> interviewDetails = calendarMongoQueryProcessor.filterInterviewDetails(calendar, interviewDetailId, name, yearMonth);

            // 응답을 위한 GetCalendarDTO 객체 생성
            GetCalendarDTO getCalendarDTO = new GetCalendarDTO();
            // Calendar 엔티티의 정보를 GetCalendarDTO로 매핑 (id, clientId 등)
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
    public boolean registerInterviewSchedule(PostInterviewDetailDTO postInterviewDetailDto, String clientId) {

        Calendar calendar;

        // 사용자 면접 일정 정보가 존재하는 지 확인
        if(calendarRepository.existsByClientId(clientId)){
            // 사용자의 면접 일정을 Calendar 객체로 가져옴
            calendar = calendarRepository.findByClientId(clientId);
        } else {
            // 사용자의 면접 일정이 없다면 새로운 Calendar 객체 생성
            calendar = new Calendar();
            calendar.setClientId(clientId); // 사용자 정보 설정
            calendar.setInterviewDetails(new ArrayList<>()); // 빈 면접 일정 리스트 초기화
        }

        // 새로운 InterviesDetails 객체 생성
        Calendar.InterviewDetails interviewDetails = new Calendar.InterviewDetails();
        // InterviesDetails 객체의 interviewDetailId 값 설정
        interviewDetails.setInterviewDetailId(UUID.randomUUID().toString());
        // 전달받은 DTO(PostInterviewDetailDTO)를 InterviewDetails 객체로 변환
        calendarMapper.postInterviewDetailDtoToInterviewDetails(postInterviewDetailDto, interviewDetails);
        log.info(interviewDetails.getInterviewTime().toString());
        // 변환된 interviewDetails를 Calendar의 interviewDetails 리스트에 추가
        calendar.getInterviewDetails().add(interviewDetails);
        // 업데이트된 Calendar 객체를 데이터베이스에 저장
        calendarRepository.save(calendar);
        // 면접 일정 등록 완료 후 true 반환
        return true;
    }

    // 사용자의 면접 일정 삭제
    public ResponseEntity<?> deleteInterviewSchedule(String clientId, String interviewDetailId) {
        // 사용자 면접 일정 정보가 존재하는 지 확인
        if (calendarRepository.existsByClientId(clientId)){
            // 사용자의 면접 일정을 Calendar 객체로 가져옴
            Calendar calendar = calendarRepository.findByClientId(clientId);
            boolean b = calendarMongoQueryProcessor.deleteInterview(calendar, interviewDetailId);
            if(b){
                return ResponseEntity.status(HttpStatus.OK).body("면접 일정 삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("interviewDetailId에 해당하는 면접 일정이 없습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 없습니다.");
        }

    }

    // 사용자의 면접 일정 수정
    public ResponseEntity<?> putInterviewSchedule(String clientId, String interviewDetailId, PutInterviewDetailDTO putInterviewDetailDTO) {
        // 사용자 면접 일정 정보가 존재하는 지 확인
        if (calendarRepository.existsByClientId(clientId)){
            // 사용자의 면접 일정을 Calendar 객체로 가져옴
            Calendar calendar = calendarRepository.findByClientId(clientId);
            // 주어진 면접 일정 ID(id)에 해당하는 면접 일정을 가져옴
            Calendar.InterviewDetails interviewDetail = calendarMongoQueryProcessor.findInterviewDetail(calendar, interviewDetailId);
            if (interviewDetail != null){ // 면접 일정이 존재하는 경우
                // 수정할 데이터를 받아온 DTO를 면접 일정 객체에 매핑하여 수정
                calendarMapper.putInterviewDetailDtoTOInterviewDetail(putInterviewDetailDTO, interviewDetail);
                // 수정된 Calendar 객체를 데이터베이스에 저장
                calendarRepository.save(calendar);
                return ResponseEntity.status(HttpStatus.OK).body("면접 일정 수정 성공");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("interviewDetailId에 해당하는 면접 일정이 없습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 없습니다.");
        }
    }

}
