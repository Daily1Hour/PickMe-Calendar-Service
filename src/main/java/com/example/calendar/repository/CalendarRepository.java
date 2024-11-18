package com.example.calendar.repository;

import com.example.calendar.entity.Calendar;
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
}
