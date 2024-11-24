package com.pickme.calendar.repository;

import com.pickme.calendar.entity.Calendar;
import com.pickme.calendar.exception.CustomException;
import com.pickme.calendar.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CalendarMongoQueryProcessor {

    private final MongoTemplate mongoTemplate;

    public List<Calendar> findInterviewsByCriteria(String userInfo, String position, LocalDateTime startDate, LocalDateTime endDate){
        // 사용자 정보로 기본 조건 생성 (해당 사용자의 모든 면접 일정 검색)
        Criteria criteria = Criteria.where("userInfo").is(userInfo);

        // 직무 조건이 있는 경우, 조건에 추가 (직무별 면접 일정 검색)
        Optional.ofNullable(position).ifPresent(p -> criteria.and("position").is(p));

        // 기간 조건이 있는 경우, 시작일과 종료일 범위를 조건에 추가 (특정 기간 내의 면접 일정 검색)
        if (startDate != null && endDate != null){
            criteria.and("interviewTime").gte(startDate).lte(endDate);
        } else if(startDate != null || endDate != null){
            throw new CustomException(ErrorCode.DOCUMENT_NOT_FOUND, "startDate와 endDate는 둘 다 입력되어야 합니다.");
        }

        // 위의 조건(Criteria)을 기반으로 MongoDB 쿼리 객체 생성
        Query query = new Query(criteria);

        // MongoDB에서 쿼리를 실행하여 조건에 맞는 면접 일정 목록 조회
        List<Calendar> calendarList = mongoTemplate.find(query, Calendar.class);

        return calendarList;
    }

    // 주어진 Calendar 객체에서 면접 일정 목록을 가져오는 메서드
    // 주어진 조건(name)이 null이 아니면 해당 회사 이름과 일치하는 면접 일정만 필터링하여 반환
    // 조건(name)이 null인 경우 모든 면접 일정을 반환
    public List<Calendar.InterviewDetails> filterInterviewDetails(Calendar calendar, String name) {
        return calendar.getInterviewDetails().stream() // 면접 일정 리스트를 스트림으로 변환
                .filter(interviewDetail -> name == null || interviewDetail.getCompany().getName().equals(name)) // 회사 이름 필터링
                .toList(); // 결과를 리스트로 반환
    }

    // 주어진 Calendar 객체에서 특정 면접 일정(interviewDetailId)을 삭제하는 메서드.
    public boolean deleteInterview (Calendar calendar, String interviewDetailId){
        // 스트림을 사용하여 주어진 interviewDetailId에 해당하는 면접 일정을 찾음
        Calendar.InterviewDetails result = calendar.getInterviewDetails().stream()
                .filter(interviewDetails -> interviewDetails.getInterviewDetailId().equals(interviewDetailId)) // 조건에 맞는 일정 필터링
                .findFirst() // 첫 번째 요소를 Optional로 반환
                .orElse(null);  // 결과가 없으면 null 반환

        // 해당 면접 일정이 존재하는 경우
        if(result != null){
            calendar.getInterviewDetails().remove(result); // 면접 일정 리스트에서 해당 항목 삭제
            mongoTemplate.save(calendar); // 변경된 Calendar 객체를 MongoDB에 저장
            return true;
        } else { // 조건에 맞는 면접 일정이 없는 경우
            return false;
        }
    }

}
