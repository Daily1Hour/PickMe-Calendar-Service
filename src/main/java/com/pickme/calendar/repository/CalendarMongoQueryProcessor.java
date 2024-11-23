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

}