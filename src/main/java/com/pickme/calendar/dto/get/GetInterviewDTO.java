package com.pickme.calendar.dto.get;

import lombok.Data;

import java.util.Date;

@Data
public class GetInterviewDTO {
    private String clientId;

    private String interviewDetailId;

    private GetCompanyDTO company;

    private Date interviewTime;

    private String position;

    private String category;

    private String description;
}
