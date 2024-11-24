package com.pickme.calendar.dto.put;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PutInterviewDetailDTO {

    private PutCompanyDTO company;

    private LocalDateTime interviewTime;

    private String position;

    private String category;

    private String description;
}
