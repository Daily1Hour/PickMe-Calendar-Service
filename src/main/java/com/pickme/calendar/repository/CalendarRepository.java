package com.pickme.calendar.repository;

import com.pickme.calendar.entity.Calendar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends MongoRepository<Calendar, String> {

    // 해당 사용자의 저장된 면접 일정이 있는지 확인
    boolean existsByClientId(String clientId);

    // 해당 사용자 면접 일정 정보 추출
    Calendar findByClientId(String clientId);

}
