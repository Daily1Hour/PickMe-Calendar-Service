package com.example.calendar.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetInterviewListDto {
    private String id;
    private String userInfo;
    private String location;
    private String companyName;
    private LocalDateTime interviewTime;
    private String position;
}
