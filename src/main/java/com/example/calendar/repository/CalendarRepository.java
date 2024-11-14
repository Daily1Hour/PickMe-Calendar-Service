package com.example.calendar.repository;

import com.example.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    // 해당 사용자의 면접 일정 전체 조회 jpa
    List<Calendar> findByUserInfo(String userInfo);
}
