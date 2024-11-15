package com.example.calendar.repository;

import com.example.calendar.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar, String> {

    // 해당 사용자의 면접 일정 전체 조회
    List<Calendar> findByUserInfo(String userInfo);
}
