package com.pickme.calendar.repository;

import com.pickme.calendar.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar, String> {

    // 해당 사용자의 특정 id 면접 일정 조회
    Calendar findByUserInfoAndId(String userInfo, String id);

    // 해당 사용자의 저장된 면접 일정이 있는지 확인
    boolean existsByUserInfo(String userInfo);

    // 해당 사용자 면접 일정 정보 추출
    Calendar findByUserInfo(String userInfo);

    // 해당 사용자의 특정 id 면접 일정 삭제
    void deleteByUserInfoAndId(String userInfo, String id);
}
