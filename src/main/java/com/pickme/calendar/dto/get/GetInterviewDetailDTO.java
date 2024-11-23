package com.pickme.calendar.dto.get;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetInterviewDetailDTO {

    private GetCompanyDTO company;

    private LocalDateTime interviewTime;

    private String position;

    private String category;

    private String description;

}
