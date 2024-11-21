package com.pickme.calendar.repository;

import com.pickme.calendar.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar, String> {

    // 해당 사용자의 면접 일정 전체 조회
    List<Calendar> findByUserInfo(String userInfo);

    // 해당 사용자의 직무별 면접 일정 조회
    List<Calendar> findByUserInfoAndPosition(String userInfo, String position);

    // 해당 사용자의 특정 기간 내 면접 일정 조회
    List<Calendar> findByUserInfoAndInterviewTimeBetween(String userInfo, LocalDateTime startDate, LocalDateTime endDate);

    // 해당 사용자의 특정 직무의 특정 기간 면접 일정 조회
    List<Calendar> findByUserInfoAndPositionAndInterviewTimeBetween(String userInfo, String position, LocalDateTime startDate, LocalDateTime endDate);

    // 해당 사용자의 특정 id 면접 일정 조회
    Calendar findByUserInfoAndId(String userInfo, String id);

    // 해당 사용자의 특정 id 면접 일정 삭제
    void deleteByUserInfoAndId(String userInfo, String id);
}
