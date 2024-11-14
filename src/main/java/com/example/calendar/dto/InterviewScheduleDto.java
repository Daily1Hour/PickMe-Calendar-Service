package com.example.calendar.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class InterviewScheduleDto {

    private Long id;

    private String userInfo;

    private String location;

    private String companyName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime interviewTime;

    private String position;
}
