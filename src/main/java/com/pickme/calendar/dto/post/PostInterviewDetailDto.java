package com.pickme.calendar.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostInterviewDetailDto {

    private String companyName;

    private String location;

    private LocalDateTime interviewTime;

    private String position;

    private String category;

    private String description;

}