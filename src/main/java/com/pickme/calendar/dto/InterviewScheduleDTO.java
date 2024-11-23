package com.pickme.calendar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class InterviewScheduleDTO {

    // id는 자동생성임으로 필요없고, userInfo는 token으로 받아 오기 때문에 필요없음

    private String location;

    private String companyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime interviewTime;

    private String position;
}
